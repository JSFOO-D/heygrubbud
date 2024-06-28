# Getting Started Hey Grub Demo

### Guides
The following guides illustrate how to use some features concretely:

* Import project as existing maven project.
* Right click on pom.xml and click Add as Maven project
* Click the play button on the toolbar where it says HeygrubbudApplication
* Open browser and enter localhost:8080
* Enter username to start chatting
  * Note that special access has been given to 'john1' as the admin/initiator of the chatroom
* Open another browser/tab and enter localhost:8080
* Enter a different username
* You may start chatting in the chat window
* When you key in 'Location: <location/cafe/restaurant>', this location will be stored for randomising by the admin/initiator
* As an initiator you may choose when to generate a random location by clicking the 'Generate Random Location' and it will be sent to the chatroom


# Description of requirements and what has been done

### Task
1. A user can initiate a session and invite others to join it.
   - Only managed to create a single chat room where users are able to join by entering their username. 
   - Did not manage to allow user/host to initiate a session to invite others to join it.

2. Other users who have joined the session may submit a restaurant of their choice.
   - Users who joined, are able to submit a restaurant location of their choice by keying in 'Location: <location>'

3. All users in the session are able to see restaurants that others have submitted.
   - Users are able to see the submission from the chat room

4. The user who initiated the session is able to end the session.
   - Only manage to create a logout button for each individual users

a. At the end of a session, a restaurant is randomly picked from all submitted
restaurants. All users in the session are then able to see the selected restaurant.
  - The main host is able to generate a random restaurant location from the list. 
  - The list will be cleared upon generation. 
  - All the users in the room are able to see the selected restaurant location.

b. A user should not be able to join a session that has already ended.
  - Currently, it is only established as 1 chatroom only. Whoever keys in the username and join will be joining this 1 chatroom.
  - Did not manage to split the rooms by session.

### Expected Engineering Qualities

1. How would you ensure that one user’s submitted location does not cause issues on
   other users’ displays?
   - Sanitize text using createTextNode() to escape the content and for all browsers to support it equally

2. How would you assure others that your application meets the requirements, and
   continues to meet the requirements even after changes have been made to it?
   - By implementing rigorous unit tests and ensure that all tests pass after any changes. 
   - If any tests fail, utilize the method red green refactor where the test case will fail first, and anyone working on the application has to ensure that it passes before refactoring the code to keep it clean, short and simple.

3. How would you ensure that your application can be deployed to serve an increasing
   number of users who may be concurrently using your service?
   - Utilize cloud where auto-scaling and eventbridge features can be used such as AWS ASG and AWS EventBridge. 
   - Should an event where the load of the instance hits a threshold, it will trigger the auto-scaling group to auto start up a new instance. 
   - This application also has to have an auto load balancer (ALB) to ensure that traffic is being routed correctly to the correct instance and load is being split evenly to prevent over loading of 1 instance.

4. How would you help a fellow team member who may need to debug your application
   in future?
   - I will ask the fellow team member to start the application in debug mode.
   - Include debugging logs into each functionality and logic so that it is easy to read and understand where the problem lies when there is any issue. 
   - I will also guide them on the logic and relate it back to the logs. 
   - Through this way, they will understand the rationale and reason behind each logic and logging purpose.


### Expected Artifacts

1. Commit your code to a github repository and send its link to us via email.
   - Refer to this link https://github.com/JSFOO-D/heygrubbud

2. Your repository should include a document explaining how your application is
   expected to be cloned and run, including any necessary preparations of the
   environment.
   - please refer to Readme.md

3. Any APIs that have been developed as part of your application should also be
   documented.
- 2 APIs and 1 listener
- @MessageMapping("/chat.sendMessage")
  @SendTo("/topic/public")
  - This API is being used to allow all messages to be sent to the public chat room.
- @MessageMapping("/chat.addUser")
  @SendTo("/topic/public")
  - This API is being used to allow any users who join the chat room to be sent to the chat room.
- handleWebSocketDisconnectListener
  - Additional webSocket event listener to allow whichever user that disconnects by clicking or closing of tab to be sent to the public chat room that they have left.


4. Significant design decisions should also be documented to facilitate future
   discussions.
   - Future designs to be added, non-exhaustive list
     - To split this into individual rooms where user can join
     - To add proper session handling of the individual chat room
     - To segregate the business logic into service layers
     - To allow private messaging to individual users
     - To allow message being broadcast to be sanitized and checked
     - To add regex validation for username
     - To add account administration such as managing own user's account
     - To allow registration of users
     - To add authentication (password) and security
     - To allow the message of the result location to be sent via SMS/Email/Whatsapp to all the users
     - To possibly allow user to key in hawker/restaurant name for it to sync with Google Maps
     - To allow user to share the location as a Google Maps link for easy navigation