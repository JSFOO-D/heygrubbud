'use strict';

// init component id
var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connecting = document.querySelector('.connecting');
var logoutForm = document.querySelector('#logoutForm');
var randomButtonForm = document.querySelector('#randomButtonForm');

// setting up websocket and users
var stompClient = null;
var username = null;
var locationList = [];

// randomise color for users
const colors = [
    '#ffc100', '#ff9a00', '#22c5cc', '#e99f9c',
    '#aaaaaa', '#96ceb4', '#ffbbff', '#ba9ade',
    '#62b0da', '#96aad3', '#92e459', '#befe7f'
];

//allow clients to connect
function connect(event){
    username = document.querySelector('#name').value.trim();
    console.log("username : " + username);

    // if username exists, switch from usernamePage to chatPage and establish a connection over websocket
    if(username) {
        // Assume john1 is admin/session initiator, he has the option to generate the random location
        if(username === 'john1') {
            randomButtonForm.classList.remove('hidden');
        }
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

// Upon connected what to do
function onConnected() {
    // subscribe to public topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // inform server of your username
    stompClient.send("/app/chat.addUser",
        {}, JSON.stringify({sender: username, type: 'JOIN'})
    )
    // remove connecting as user is already connected to the chat
    connecting.classList.add('hidden');
}

// if any error show error message in red
function onError(error) {
    connecting.textContent = 'Could not connect to Websocket Server. Please refresh this page to try again later.';
    connecting.style.color = 'red';
}

// send message using the button
function sendMessage(event) {
    // init messageContent
    var messageContent = messageInput.value.trim();
    var messageIsLocation = false;
    console.log("messageContent : " + messageContent);

    // if message content contains value and user is connected through websocket
    if (messageContent && stompClient) {
        if(messageContent.toUpperCase().includes('LOCATION:')){
            messageIsLocation = true;
        }
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: messageIsLocation ? 'LOCATION' : 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        // After sending message to clear the message
        messageInput.value = '';
    }
    event.preventDefault();
}

// upon message received and process the message
function onMessageReceived(payload) {
    // reading of payload
    var message = JSON.parse(payload.body);
    console.log("Receiving message : " + message);

    //creating message element
    var messageElement = document.createElement('li');

    // differentiate type of message and what to display
    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if(message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else if(message.type === 'RESTAURANT') {

    } else {
        messageElement.classList.add('chat-message');
        //create avatar element for different user
        var avatarElement = document.createElement('i');
        // sanitize text
        var avatarText = document.createTextNode(message.sender[0]);
        console.log("avatarText : " + avatarText)
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        // sanitize text
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);
    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

//randomise generator for username's avatar color
function getAvatarColor(messageSender) {
    var hash = 0;
    for(var i = 0; i < messageSender.length; i++){
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

// logout for user
function logout(event){
    username = document.querySelector('#name').value.trim();
    console.log("username logging out : " + username);

    // if username exists, switch from chatPage to usernamePage and close the websocket connection
    if(username) {
        chatPage.classList.add('hidden');
        usernamePage.classList.remove('hidden');

        if (stompClient !== null) {
            stompClient.disconnect();
        }
    }
    event.preventDefault();
}

// randomise location
function generateRandomLocation(event){

    // request to generate random location
    var chatMessage = {
        sender: username,
        content: '',
        type: 'GENERATE'
    };
    // trigger a sendMessage
    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
    event.preventDefault();
}


usernameForm.addEventListener('submit', connect, true);
messageForm.addEventListener('submit', sendMessage, true);
logoutForm.addEventListener('submit', logout, true);
randomButtonForm.addEventListener('submit', generateRandomLocation, true);


