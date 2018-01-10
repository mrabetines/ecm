package listeners;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
//*******
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
public class EndExecutionListener implements ExecutionListener {
	  @Override  
	  public void notify(DelegateExecution execution) throws Exception 
	  {    // TODO Auto-generated method stub    
		  //long approved_amount_limit;        
		  //approved_amount_limit=50000;    
		  System.out.println("******************the End event Listener is Called************");    
		  //execution.setVariable("approved_amount_limit", approved_amount_limit);  
		  try {

				URL url = new URL("http://localhost:9763/endpoints/EndEventReceiver");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				String id= execution.getId();
				long date=new Date().getTime();

				String input = "{\"event\":{\"payloadData\":{\"id\":\""+id+"\",\"date\":"+date+"}}}";

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
	  

