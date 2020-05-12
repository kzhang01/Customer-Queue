import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Load {
	public static void customersFile(String file) {
		File customersFile = new File(file);
	    BufferedReader br = null;
	    try{
	    	br = new BufferedReader(new FileReader(customersFile));
	    	/*
	    	 * Marks beginning of txt file
	    	 */
	    	br.mark(1);
	    	Main.T = Integer.parseInt(br.readLine());	
	        while ((br.readLine()) != null) {
	        	/*
	        	 * Returns to mark to ensure the br.readLine() condition of the while loop
	        	 * doesn't affect the reading of the txt file 
	        	 */
	        	br.reset();
	    		/*
	    		 * Reads ID Number from txt file and assigns to variable id
	    		 */
	    		while(br.read() != ':') {
	            	br.read();
	            }
	            br.read();
	            br.read();
	            int id = Integer.parseInt(br.readLine());
	            
	            /*
	             * Reads Arrival Time from txt file and assigns to variable time
	             */
	            while(br.read() != ':') {
	            	br.read();
	            }
	            br.read();
	            String timeString = br.readLine();
	            int time = Main.convertToSeconds(timeString);
	        
	            /*
	             * Create new Customer using id and arrival time from before
	             */
	            Main.line.enqueue(new Customer(id, time));
	            
	            /*
	             * Calculates departure time and assigns it to Customer
	             */
	            int count = 1;
	            int departureTime = 0;
	            while(Main.line.position.data.getId() != id) {
	            	Main.line.position = Main.line.position.next;
	            }
	            /*
	             * Calculates departure time if they arrive before 9 AM
	             */
	            if(time < Main.OPENING) {
	            	while(Main.line.position.prev != null) {
	            		
	            		++count;
	            		Main.line.position = Main.line.position.prev;
	            	}
	            	
	            	departureTime = Main.OPENING + (count * Main.T);
	            }
	            /*
	             * Calculates departure time if they arrive after 9 AM
	             */
	            else {
	            	while(Main.line.position.prev != null && Main.line.position.prev.data.getTime() + Main.T >= Main.line.position.data.getTime()) {
	            		++count;
	            		Main.line.position = Main.line.position.prev;
	            	}
	            	departureTime = Main.line.position.data.getTime() + (count * Main.T);
	            }
	            
	            Main.line.last.data.setDepartureTime(departureTime);
	            /*
		         * Sets mark to before while loop condition moves it
		         */
	            br.mark(1);

	        }
	        
	        br.close();
	    }catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	public static void queriesFile(String file) throws IOException {
		FileWriter output = new FileWriter("output.txt");
		File queriesFile = new File(file);
	    BufferedReader br = null;
	    try{
	    	br = new BufferedReader(new FileReader(queriesFile));
	    	/*
	    	 * Marks beginning of txt file
	    	 */
	    	br.mark(1);
	    		
	        while ((br.readLine()) != null) {
	        	/*
	        	 * Returns to mark to ensure the br.readLine() condition of the while loop
	        	 * doesn't affect the reading of the txt file 
	        	 */
	        	br.reset();
	        	String query = br.readLine();
	        	
	        	switch(query) {
	        		case "NUMBER-OF-CUSTOMERS-SERVED":
	        			System.out.println("NUMBER-OF-CUSTOMERS-SERVED: " + Main.numServed(Main.line));
	        			output.write("NUMBER-OF-CUSTOMERS-SERVED: " + Main.numServed(Main.line) + "\r\n");
	        			break;
	        			
	        		case "LONGEST-BREAK-LENGTH":
	        			System.out.println("LONGEST-BREAK-LENGTH: " + Main.longestBreak(Main.line));
	        			output.write("LONGEST-BREAK-LENGTH: " + Main.longestBreak(Main.line) + "\r\n");
	        			break;
	        			
	        		case "TOTAL-IDLE-TIME":
	        			System.out.println("TOTAL-IDLE-TIME: " + Main.totalIdle(Main.line));
	        			output.write("TOTAL-IDLE-TIME: " + Main.totalIdle(Main.line) + "\n");
	        			break;
	        			
	        		case "MAXIMUM-NUMBER-OF-PEOPLE-IN-QUEUE-AT-ANY-TIME":
	        			System.out.println("MAXIMUM-NUMBER-OF-PEOPLE-IN-QUEUE-AT-ANY-TIME: " + Main.maxPplInLine(Main.line));
	        			output.write("MAXIMUM-NUMBER-OF-PEOPLE-IN-QUEUE-AT-ANY-TIME: " + Main.maxPplInLine(Main.line) + "\n");
	        			break;
	        			
	        		default:
	        			if(query.contains("WAITING-TIME-OF")) {	
	        				int customerNum = Integer.parseInt(query.substring(query.length() - 1));
	        				Customer target = null;
	        				/*
	        				 * reset position because we moved it forward when calculating
	        				 * departureTimes in Load.customersFile
	        				 */
	        				Main.resetPosition();
	        				while(Main.line.hasNext()) {
	        					if(Main.line.position.data.getId() == customerNum) {
	        						target = Main.line.position.data;
	        						break;
	        					}
	        					Main.line.next();
	        				}
	        				
	        				if(target == null) {
	        					System.out.println("Customer not found");
	        					break;
	        				}
	        				
	        				System.out.println("WAITING-TIME-OF " + target.getId()+ ": " + Main.findWaitingTime(target));
	        				output.write("WAITING-TIME-OF " + target.getId()+ ": " + Main.findWaitingTime(target) + "\n");
	        			}else
	        				System.out.println("try again, something goofed");
	        			break;
	        	}
	        	br.mark(1);
	        }
	        br.close();
	        output.close();
	    }catch (IOException e) {
	            e.printStackTrace();
	        }
	}
}
