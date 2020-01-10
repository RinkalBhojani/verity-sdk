package com.evernym.verity.sdk.protocols;

import com.evernym.verity.sdk.exceptions.UndefinedContextException;
import com.evernym.verity.sdk.exceptions.VerityException;
import com.evernym.verity.sdk.exceptions.WalletException;
import com.evernym.verity.sdk.utils.Context;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Builds and sends an encrypted agent message to Verity asking Verity to 
 * write a new Schema to the ledger on behalf of the 
 * SDK/enterprise.
 */

public class WriteSchemaImpl extends Protocol implements WriteSchema {

    String name;
    String version;
    String[] attrs;

    /**
     * Creates a Schema from a list of attributes
     * @param attrs Attributes name for schema
     */
    WriteSchemaImpl(String name, String version, String ...attrs) {
        super();
        this.name = name;
        this.version = version;
        this.attrs = attrs;
    }

    @Override
    protected void defineMessages() {
        throw new UnsupportedOperationException("DO NOT USE");
    }


    /**
     * Sends the write request message to Verity
     * @param context an instance of Context configured with the results of the provision_sdk.py script
     * @throws IOException when the HTTP library fails to post to the agency endpoint
     * @throws UndefinedContextException when the context doesn't have enough information for this operation
     * @throws WalletException when there are issues with encryption and decryption
     */
    @SuppressWarnings("WeakerAccess")
    public void write(Context context) throws IOException, VerityException {
        this.send(context, writeMsg(context));
    }

    @Override
    public JSONObject writeMsg(Context context) {
        JSONObject message = new JSONObject();
        message.put("@type", getMessageType(WRITE_SCHEMA));
        message.put("@id", getNewId());
        message.put("name", this.name);
        message.put("version", this.version);
        message.put("attrNames", new JSONArray(attrs));
        return message;
    }

    @Override
    public byte[] writeMsgPacked(Context context) throws VerityException {
        return this.packMsg(context, writeMsg(context));
    }

}