/*
 * Copyright (c) 2016 David Green.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package david.a.t.green.springsocksexample.spring;

import java.security.Principal;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

/**
 *
 * @author David
 */
@Controller
public class PersonController
{
	//To scale this, write to some in-memory database instead of a concurrent hash map
	private final Set<String> readiedUsers = ConcurrentHashMap.newKeySet();
	
	/**
	 * Sets a calling principal to be either ready or not ready.
	 * Typically called by the client to notify the server that they are ready to be activated.
	 * 
	 * @param ready true if the client is ready
	 * @param principal the associated security principal
	 * 
	 * @return acknowledging message sent to the principal's personal queue
	 */
	@MessageMapping("/person")
	//@SendTo("/queue/person")
	@SendToUser
	public String processPerson(boolean ready, Principal principal)
	{
		String username = principal.getName();
		
		if (ready) readiedUsers.add(username);
		else readiedUsers.remove(username);
		
		return ready ? "READY" : "Not Ready";
	}	

	/**
	 * Returns a set of principal usernames that have declared themselves ready for activation.
	 * 
	 * @return the set of principal usernames
	 */
	@Bean
	public Set<String> getReadiedUsers()
	{
		return readiedUsers;
	}	
}