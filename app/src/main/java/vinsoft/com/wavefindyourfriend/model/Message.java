package vinsoft.com.wavefindyourfriend.model;

/**
 * Created by DONG on 05-Apr-17.
 */

public class Message  {
    String messageID;
    String personSendID;
    String contentMessage;
    String dateSend;
    String timeSend;
    String messageVision;//trangj thai xem chua

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getPersonSendID() {
        return personSendID;
    }

    public void setPersonSendID(String personSendID) {
        this.personSendID = personSendID;
    }

    public String getContentMessage() {
        return contentMessage;
    }

    public void setContentMessage(String contentMessage) {
        this.contentMessage = contentMessage;
    }

    public String getDateSend() {
        return dateSend;
    }

    public void setDateSend(String dateSend) {
        this.dateSend = dateSend;
    }

    public String getTimeSend() {
        return timeSend;
    }

    public void setTimeSend(String timeSend) {
        this.timeSend = timeSend;
    }

    public String getMessageVision() {
        return messageVision;
    }

    public void setMessageVision(String messageVision) {
        this.messageVision = messageVision;
    }


}
