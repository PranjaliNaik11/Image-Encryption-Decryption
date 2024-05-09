# Image Encryption Decryption Java Project with Blowfish Algorithm

## Overview

This Java project demonstrates image encryption and decryption using the Blowfish algorithm within a client-server architecture with chat functionality. It provides a secure means of transferring images between clients through encryption and decryption mechanisms. The chat server facilitates communication between multiple clients while ensuring the confidentiality of exchanged messages and images using Blowfish encryption.

## Features

- **Image Encryption**: Images are encrypted using the Blowfish algorithm before transmission to ensure data security and privacy.
- **Image Decryption**: Encrypted images received by the server are decrypted using the Blowfish algorithm before delivery to clients.
- **Chat Functionality**: Clients can communicate with each other through the chat server, enhancing collaboration and interaction.
- **Client-Server Architecture**: Utilizes a client-server model for managing connections and facilitating data exchange.
- **Multi-Client Support**: Allows multiple clients to connect to the server simultaneously and exchange messages and images securely.

## How to Use

### Prerequisites

- Java Development Kit (JDK) installed on your system.
- Basic understanding of Java programming and networking concepts.

### Installation

1. Clone the project repository from [GitHub](https://github.com/PranjaliNaik11/image-encryption-decryption-blowfish-java).

   ```bash
   git clone https://github.com/example/image-encryption-decryption-blowfish-java.git
   ```

2. Navigate to the project directory.

   ```bash
   cd image-encryption-decryption-blowfish-java
   ```

### Usage

1. Start the chat server by executing the `chatserver.java` file.

   ```bash
   java chatServer
   ```

2. Launch the client application by running the `chatclient.java` file.

   ```bash
   java chatClient
   ```

3. Follow the prompts to connect to the chat server and begin sending messages and images securely.

## Contributing

Contributions are welcome! If you'd like to contribute to this project, please fork the repository, make your changes, and submit a pull request. Make sure to follow the existing code style and guidelines.
