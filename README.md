# EncrypterMod v1.0.0
**Description:** Client-side AES chat encryption for Minecraft. Encrypt messages for friends, send them with a unique ID, and decrypt them with the same key. Fully works on player chat[NotSecure] channel and system chat [SYSTEMCHAT] channel, which most servers have nowdays.

---

## **Features**

* Secure AES-GCM encrypted chat messages for Minecraft anarchy servers.
* works on almost any server. (still need testing) working servers: Constantiam, 2b2t, Oldfag, anarchy.ac, crystalpvp.cc, 6b6t, 9b9t
* Unique message IDs for each sent message
* Chat listener works for both player and system chat
* Local storage of encrypted messages for decryption
* Commands to send, decrypt, and view mod info
* Fully client-side: no server modification required
* Optional offline decryption using a Python/HTML tool

---

## **Installation**

1. Place the mod `.jar` in your `mods` folder for your Minecraft Fabric client.
2. Start Minecraft with Fabric.
3. The mod will automatically create a default AES key in `config/encrypter/key.json`.
4. Commands and chat listener are registered on client initialization.

---

## **Commands**

* `/encrypter info`
  Prints mod info in chat (version, authors, description).

* `/encrypter encrypt <message>`
  Encrypts a message and sends it with a unique ID. Example:

  ```
  [ID: Player_abc123-uuid] EncryptedMessageHere
  ```

* `/encrypter decrypt <id>`
  Decrypts a previously received encrypted message stored locally.

---

## **How It Works**

### **Key Management**

* `ConfigHandler` handles loading and saving the AES key.
* `KeyConfigHandler` reads/writes the key from `config/encrypter/key.json`.
* Default key created if no config exists.

### **Message Flow**

1. **Sending Messages:**

   * `ChatHandler.sendEncryptedMessage` encrypts the message with AES-GCM using the client’s key.
   * Generates a unique ID for tracking.
   * Sends formatted message: `[ID: <id>] <encryptedMessage>`.
2. **Receiving Messages:**

   * `ChatListener` scans chat messages using a regex for `[ID: <id>] <encryptedMessage>`.
   * Messages detected are stored in `ChatHandler` and notification is shown.
3. **Decryption:**

   * Messages can be decrypted locally with the same AES key.
   * If keys mismatch, decryption fails.

### **Encryption Details**

* AES in **GCM mode** with 128-bit authentication tag
* 12-byte random IV for every message
* Ciphertext is Base64-encoded (URL-safe) for safe Minecraft chat transmission
* Messages include IV prepended to ciphertext for proper decryption

---

## **Offline Decryption**

Users who do not want to run the mod can decrypt messages offline using:

* **Python CLI script:** Paste encrypted message and key (not provided yet)
* **HTML/JS tool:** Fully offline in browser (not provided yet)

Example Python usage:

```bash
python decrypt_cli.py "EncryptedMessageHere" --key "1234567890abcdef1234567890abcdef"
```

---

## **Classes Overview**

* `EncrypterMod` – Initializes the mod, loads key, registers commands & chat listener.
* `ConfigHandler` – Loads/saves AES key via `KeyConfigHandler`.
* `KeyConfigHandler` – Handles JSON read/write of AES key.
* `EncryptionUtils` – AES encryption/decryption logic.
* `ChatHandler` – Stores messages, handles sending and local decryption.
* `ChatListener` – Listens for messages in chat and system channels, detects message IDs.
* `CommandHandler` – Registers `/encrypter` commands.

---

## **Notes & Tips**

* Always change default key! Key generation will give every new user the same key. INSTANT CHANGE IT
* Share the AES key with friends only.
* Changing your key will prevent others from decrypting previous messages.
* System chat detection works for servers that send `[System]` messages.
* ASCII art or long messages you send may require careful formatting to display properly.

---

## **Future Improvements**

* Secure key generation / Unique Per-user keys 32 bit
* Offline message persistence
* Click-to-copy encrypted messages (maybe)
* Error reporting for key mismatches (maybe)

---

## **License**

basic aah github license opensorce 
