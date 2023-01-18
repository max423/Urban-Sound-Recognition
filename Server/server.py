# FATTO RIVEDERE MESSAGGI
import re
import jpysocket
import socket
import joblib

SIMPLE_PACKET_SIZE = 1024
FILE_PACKET_SIZE = 4 * 1024

def start_server():
    """
    Starts the server (single-process server)
    """
    host = 'localhost'  # Host Name
    port = 5000  # Port Number
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # Create Socket
    s.bind((host, port))  # Bind Port And Host
    s.listen(10)  # listening
    # encoder = joblib.load('classifier/encoder.pkl')
    # tfidf_vectorizer = joblib.load('classifier/tfidf_vectorizer.pkl')
    select_k = joblib.load('classifier/select_k.pkl')
    clf = joblib.load('classifier/svc.pkl')
    print("Server started ... ")

    while True:
        connection, address = s.accept()  # Accept the Connection
        msg_recv = connection.recv(SIMPLE_PACKET_SIZE)  # Receive msg
        msg_recv = jpysocket.jpydecode(msg_recv)  # Decrypt msg
        if msg_recv == "SendSound":
            # RICEZIONE RICHIESTA DI CLASSIFICAZIONE
            # GESTIRE FILE AUDIO (OPPURE GIA' IL FILE CON LE 40 FEATURE?)
            print("\nRequest for sound classification")
            msg_recv = connection.recv(SIMPLE_PACKET_SIZE)
            size = int((int(jpysocket.jpydecode(msg_recv))) / FILE_PACKET_SIZE) + 1
            f = open("./data/filmPlot.txt", 'wb')
            print("Download of the sound ...")
            while size > 0:
                packet = connection.recv(FILE_PACKET_SIZE)
                f.write(packet)
                size -= 1
            f.close()
            print("Classifing genre ...")

            with open('./data/filmPlot.txt', 'r') as file:
                input = file.read().rstrip()

            print(input)
            input = [input]
            # X_new_counts = tfidf_vectorizer.transform(input)

            # X_new_tfidf = select_k.transform(X_new_counts)
            X_new = select_k.transform(input)

            # predicted = clf.predict(X_new_tfidf)
            predicted = clf.predict(X_new)

            # genre = encoder.inverse_transform(predicted)
            genre = predicted

            msg = str(genre)
            cleanText = re.sub(r"[\[\]'']", "", msg)
            print("To Client: ", cleanText)
            msgsend = jpysocket.jpyencode(cleanText)
            connection.send(msgsend)
        connection.close()