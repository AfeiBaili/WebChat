export class Message {
    messageId
    userId
    userName
    message
    messageDate

    constructor(messageId, userId, userName, message, messageDate) {
        this.messageId = messageId;
        this.userId = userId;
        this.userName = userName;
        this.message = message;
        this.messageDate = messageDate;
    }
}