package com.example.lento.demotest.contact;

/**
 * Created by lento on 2017/8/31.
 */

public class ContactItem {
    long rawContactId;
    String name;
    String phoneNumber;
    String photoUri;

    public ContactItem(long rawContactId, String number, String name, String photoUri) {
        this.rawContactId = rawContactId;
        this.phoneNumber = number;
        this.name = name;
        this.photoUri = photoUri;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("rawContactId = ");
        stringBuilder.append(rawContactId);
        stringBuilder.append(", phoneNumber = ");
        stringBuilder.append(phoneNumber);
        stringBuilder.append(", name = ");
        stringBuilder.append(name);
        stringBuilder.append(", photoUri = ");
        stringBuilder.append(photoUri);
        return stringBuilder.toString();
    }
}
