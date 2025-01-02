import requests
import json

class User:
    def __init__(self, username, password):
        self.username = username
        self.password = password

# Create a new User object
user = User("luo", "123")

# Convert the User object to a JSON payload
payload = json.dumps(user.__dict__)

# Set the headers
headers = {
    'Content-Type': 'application/json'
}

# Make the POST request to localhost:9090
response = requests.post("http://localhost:9090/user/query", headers=headers, data=payload)

# Check the response
if response.status_code == 200:
    print("POST request successful!")
    print(response.text)
else:
    print("POST request failed with status code:", response.status_code)
    print(response.text)