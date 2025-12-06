# EncrypterMod v1.0.0
**Description:** Client-side AES chat encryption for Minecraft. Encrypt messages for friends, send them with a unique ID, and decrypt them with the same key. Fully works on player [NotSecure]Chat; channel and system chat [SYSTEM]Chat; channel, which most servers have nowdays. Depends on fabricAPI.

---

## **Features**

* Secure AES-GCM encrypted chat messages for Minecraft anarchy servers.
* works on almost any server. (still need testing) working servers: Constantiam.net, Oldfag.org, anarchy.ac, crystalpvp.cc, 9b9t, DonutSMP, Qndres.net, V+
* Unique message IDs for each sent message
* Chat listener works for both player and system chat
* Local storage of encrypted messages for decryption
* Commands to send, decrypt, and view mod info
* Fully client-side: no server modification required
* Optional offline decryption using a Python/HTML tool

---
# Offline Encryption/Decryption tools fully HTML NO DOWNLOAD OF JAR NEEDED

## **Offline Decryption Tool (HTML – No install needed)**
If for some reason you do not want to download the mod to decrypt messages, you can use the Offline html tool privided inside Offline_Decryption_Folder.
What to do? On github:
1. Open Offline_Decryption_Folder.
2. open decrypter_encrypter file inside the folder.
3. then; you have 2 options now. Option A: Download the full html file (click 3 dots on the top right, download). Option B: Select full code, scan with any maliciousintentscanner, copypasta the code in a empty txt file on your desktop,save the file as .html NOT AS .txt You need to select the option "save as '.*' instead of .txt
4. doubleclick encrypter_decrypter.html it should open the html tool inside your browser. 
5. open minecraft logs and copypaste the full encryted message (ID:Playername_MessageUUID_EncryptedTEXT) inside the decrypter. DO not take a screenshot from minecraft ingame chat and use a tool like ImageToText Ai to retrieve the encrypted text + message uuid it will format it weirdly and not work correctly.
6. enter the key from /minecraft/config/encrypter/key.json
7. Done -> You can see the encrypted message from your friend without downloading the jar.

---
## **Offline Encryption Tool (HTML - No install needed)
1. Open Offline_Encrypter_Folder.
2. open encrypter_encrypter file inside the folder.
3. then; you have 2 options now. Option A: Download the full html file (click 3 dots on the top right, download). Option B: Select full code, scan with any maliciousintentscanner, copypasta the code in a empty txt file on your desktop,save the file as .html NOT AS .txt You need to select the option "save as '.*' instead of .txt
4. doubleclick encrypter_encrypter.html it should open the html tool inside your browser. 
5. Enter; username, shared aes-key >Raw text key (UTF-8) — paste exactly what's in config/key.json< (same as your friends), and your message.
6. Click on "Encrypt and generate chatline" 
7. Done -> You can copy pasta the ID_username_UUID + Encrypted message in your Minecraft chat now for your friend to decode without downloading the jar.

---
# **Important note for AES-key (For mod only, does not affect offline encryption tool)
As for now every new user will get the same default key when fist launching the mod.

```java
 { JsonObject json = new JsonObject();
            json.addProperty("aes_key", "1234567890abcdef1234567890abcdef");
            }
```


This will be improved in the future.

### CHANGE KEY IMMEDIATELY!
The Key HAS to be 32 bits (32 ASCII Character 1 char = 1 bit) make sure your key has 32 letters/numbers/symbols!!!

## **Installation for the actual Mod**

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

* `/encrypter toggleAutoDecrypt`
  Auto-Decrypts messages if you you have the same aes key like your friend(group)   

---

## **How It Works**

### **Key Management**

* `ConfigHandler` handles loading and saving the AES key.
* `KeyConfigHandler` reads/writes the key from `config/encrypter/key.json`.
* Default key created if no config exists. CHANGE KEY IMMEDIATELY AFTER LAUNCH, THEN RELAUNCH GAME WITH NEW KEY FOR BEST PRIVACY , currently ALL new users will get the same base key.

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


* **HTML/JS tool:** Fully offline in browser -> Inside OfflineDecryption folder

Offline Decryption Tool (HTML – No Install Needed)
For users who prefer not to install the mod, EncrypterMod provides a fully offline, browser-based decrypter.
You can use it to decrypt any message as long as you have correct AES key.
The tool uses the browser’s built-in WebCrypto AES-GCM engine — the same algorithm used in the mod — ensuring full compatibility.
Cross-platform (Windows, macOS, Linux)
For players who want to decrypt messages without installing the mod

File Location in Repository
/tools/offline_decrypter.html

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

* Secure key generation / Unique Per-user keys 32 bit (SOOONtm)
* Offline message persistence
* Error reporting for key mismatches (maybe)

---

## Development

* **Build System:** Gradle
* **IDE Support:** IntelliJ IDEA, Eclipse, VSCode
* **Java Version:** Compatible with Java 21+


## Credits & Shoutouts

* **Main Testserver:**
Constantiam.net, anarchy.ac (BANNED;TheBanhammerHasSpoken), Vanilla+(BANNED #inappropriateusername), Qndres.net(#BANid2016|BANNED;permanent,reason;bot)
* **Tools & Assistance:** OpenAI ChatGPT for bugfixes/codeblock generation

---

## Privacy & Safety

* No IPs or sensitive data is tracked.
* All configuration data (AES-Key) is stored locally. You must always change your key if you feel like you re being insided.
* If you do not want to download this jar. you can make your own Encryption/Decryption HTML tool (Fully Offline btw zero chance of getting hacked) by first checking the code, then make your OWN html file on PC.

## **License**

basic aah github license opensorce 
