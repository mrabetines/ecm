package listeners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;

public class MyTaskListener  implements TaskListener{
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override  
	  public void notify(DelegateTask execution)
	  {    // TODO Auto-generated method stub        
		  System.out.println("******************the Task Listener is Called************");    
		  //execution.setVariable("approved_amount_limit", approved_amount_limit);  
		  try {

				URL url = new URL("http://localhost:9763/endpoints/SalesEventReceiver");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				//long quantity= (Long) execution.getVariable("lapQuantity");
				long quantity= (Long) execution.getExecution().getVariable("laptopQuantity");
				double price=Double.parseDouble((String) execution.getVariable("productPrice"));
				String name=(String) execution.getExecution().getVariable("laptopName");

				String input = "{\"event\":{\"payloadData\":{\"quantity\":\""+quantity+"\",\"price\":"+price+",\"name\":"+name+"}}}";

				OutputStream os = conn.getOutputStream();
				os.write(input.getBytes());
				os.flush();

				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
				}

				
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));

				/*String output;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					System.out.println(output);
				}*/

				conn.disconnect();

			  } catch (MalformedURLException e) {

				e.printStackTrace();

			  } catch (IOException e) {

				e.printStackTrace();

			 }

		  } 
		  }
	  
	  

	  