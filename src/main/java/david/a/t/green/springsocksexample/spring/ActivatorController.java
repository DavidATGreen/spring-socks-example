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

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Responsible for activating clients that have told us they are ready.
 *
 * @author David
 */
@RestController
public class ActivatorController
{
	private final Logger LOGGER = LoggerFactory.getLogger(ActivatorController.class.getName());
	@Autowired
	private Set<String> readiedUsers;
	private final SimpMessagingTemplate template;

	@Autowired
	public ActivatorController(SimpMessagingTemplate template)
	{
		this.template = template;
	}

	/**
	 * Activates all people (clients) that have marked themselves as ready.
	 * 
	 * The message "ACTIVATE" is passed to users on their personal queues /user/<code>username</code>/queue/person
	 */
	@RequestMapping("/activate")
	public void activate()
	{
		LOGGER.debug("Activate!");

		for (String user : readiedUsers)
		{
			LOGGER.debug("Sending message to " + user);
			template.convertAndSend("/user/" + user + "/queue/person", "ACTIVATE");
		}
	}
}