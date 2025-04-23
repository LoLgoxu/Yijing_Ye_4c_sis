import socket
import random
import string

def generate_random_string(length=10):
    """Genera una stringa casuale di caratteri ASCII."""
    return ''.join(random.choice(string.ascii_letters) for _ in range(length))

def run_client():
    host = '127.0.0.1'  # Indirizzo del server
    port = 12345        # Porta del server

    # Creazione del socket TCP
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.connect((host, port))
        random_str = generate_random_string()
        s.sendall(random_str.encode())  # Invia la stringa
        print(f"Inviato: {random_str}")

run_client()