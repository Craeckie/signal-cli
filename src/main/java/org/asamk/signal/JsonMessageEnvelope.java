package org.asamk.signal;

import org.whispersystems.signalservice.api.messages.SignalServiceContent;
import org.whispersystems.signalservice.api.messages.SignalServiceEnvelope;
import org.whispersystems.signalservice.api.push.SignalServiceAddress;
import org.asamk.signal.storage.contacts.ContactInfo;

class JsonMessageEnvelope {
    String source;
    String sourceName;
    int sourceDevice;
    String relay;
    long timestamp;
    boolean isReceipt;
    JsonDataMessage dataMessage;
    JsonSyncMessage syncMessage;
    JsonCallMessage callMessage;

    public JsonMessageEnvelope(SignalServiceEnvelope envelope, SignalServiceContent content, Manager m) {
        SignalServiceAddress source = envelope.getSourceAddress();
        ContactInfo sourceContact = m.getContact(source.getNumber());
        this.source = source.getNumber();
        this.sourceName = sourceContact.name;
        this.sourceDevice = envelope.getSourceDevice();
        this.relay = source.getRelay().isPresent() ? source.getRelay().get() : null;
        this.timestamp = envelope.getTimestamp();
        this.isReceipt = envelope.isReceipt();
        if (content != null) {
            if (content.getDataMessage().isPresent()) {
                this.dataMessage = new JsonDataMessage(content.getDataMessage().get(), m);
            }
            if (content.getSyncMessage().isPresent()) {
                this.syncMessage = new JsonSyncMessage(content.getSyncMessage().get(), m);
            }
            if (content.getCallMessage().isPresent()) {
                this.callMessage = new JsonCallMessage(content.getCallMessage().get());
            }
        }
    }
}
