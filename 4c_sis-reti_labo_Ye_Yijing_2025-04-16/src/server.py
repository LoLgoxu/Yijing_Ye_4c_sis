import socket

def run_server():
    host = '127.0.0.1'  # Indirizzo locale
    port = 12345        # Porta di ascolto

    # Creazione del socket TCP
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.bind((host, port))
        s.listen(1)  # Abilita un solo client in connessione
        print(f"Server in ascolto su {host}:{port}...")

        conn, addr = s.accept()  # Accetta la connessione
        with conn:
            print(f"Connessione stabilita con {addr}")
            data = conn.recv(1024)  # Riceve i dati (max 1024 byte)
            print(f"Ricevuto: {data.decode()}")

    print("Connessione chiusa.")

run_server()