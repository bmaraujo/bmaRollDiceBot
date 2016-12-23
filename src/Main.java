import java.util.List;
import services.RollService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import entities.Roll;


/**
 * 
 */

/**
 * @author Bruno Araújo
 *
 */
public class Main
{
	
	/**
	 * @param args
	 * @author Bruno Araújo
	 * @since Dec 22, 2016
	 */
	public static void main(String[] args)
	{
		
		TelegramBot bot = TelegramBotAdapter.build( "token" );
		
		GetUpdatesResponse updatesResponse;
		
		SendResponse sendResponse;
		
		BaseResponse baseResponse;
		
		int m=0;//offset
		
		try
		{
			while(true) {
				//get updates
				updatesResponse =  bot.execute(new GetUpdates().limit(100).offset(m));
				
				List<Update> updates = updatesResponse.updates();
 
				for (Update update : updates) {
					
					m = update.updateId()+1;
					
					if(update.message() != null && update.message().text() != null) {
						
						Logger.log("Received message:"+ update.message().text());
					
						String[] command = update.message().text().split( " " );
						
						if(command.length < 2) {
							sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Invalid command..."));
						}
						else {
							
							//send typing action
							baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
							
							Logger.log( "Action response sent?" + baseResponse.isOk());
							
							Roll roll = newCommand(command);
							
							if(null == roll || command[0].charAt( 0 ) != '/' || roll.getDices() < 1 ) {
								sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Invalid command..."));
							}
							else {
								RollService rollService = RollService.getInstance();
								
								String message = rollService.roll( roll);
								
								SendMessage sendMessage = new SendMessage(update.message().chat().id(),message).parseMode( ParseMode.Markdown );
								
								//Quote the user's message
								sendMessage.replyToMessageId( update.message().messageId() );
								
								//send reponse message
								sendResponse = bot.execute(sendMessage);
								
								Logger.log( "Message sent?"+sendResponse.isOk() );
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param command
	 * @return
	 * @author Bruno Araújo
	 * @since Dec 23, 2016
	 */
	private static Roll newCommand(String[] command)
	{
		Roll roll = null;
		
		try
		{
			int dices = 0;
			int sides;
			int mod = 0;
			int indexOfD = command[1].indexOf( "d" );
			int indexOfAdd = command[1].indexOf( "+" );
			int indexOfSub = command[1].indexOf( "-" );
			
			if(indexOfD > 0) {
				dices = Integer.parseInt( command[1].substring( 0,indexOfD ) );
			}
			else {
				dices = 1;
			}
			
			if(indexOfAdd > 0) {
				sides = Integer.parseInt( command[1].substring( indexOfD+1,indexOfAdd ) );
				mod = Integer.parseInt( command[1].substring( indexOfAdd+1 ) );
			}
			else if(indexOfSub > 0) {
				sides = Integer.parseInt( command[1].substring( indexOfD+1, indexOfSub ) );
				mod = Integer.parseInt( command[1].substring( indexOfSub+1 ) ) * -1;
			}
			else {
				sides = Integer.parseInt( command[1].substring( indexOfD+1 ) );
			}
			
			roll = new Roll(dices, sides, mod);
			
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		return roll;
	}

}
