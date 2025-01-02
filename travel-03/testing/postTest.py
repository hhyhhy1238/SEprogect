import requests
import json

class PostRequest:
    def __init__(self, id, owner, title, content):
        self.id = id
        self.owner = owner
        self.title = title
        self.content = content


# Create a new User object
postRequest = PostRequest("0712", "hhy", "xxx的故宫-八达岭旅游规划", "期待这次旅游")

# Convert the User object to a JSON payload
payload = json.dumps(postRequest.__dict__)


data = {
    'id': '1',
    'owner': 'hhy',
    'title': 'hhy的旅游计划',
    'content': 'ababababa'
}

# Set the headers
headers = {
    'Content-Type': 'application/json'
}

# Make the POST request to localhost:9090
# response = requests.post("http://localhost:9090/post/addWithoutImage", headers=headers, data=payload)
response = requests.post("http://localhost:9090/post/addWithoutImage",data=data)

# Check the response
if response.status_code == 200:
    print("POST request successful!")
    print(response.text)
else:
    print("POST request failed with status code:", response.status_code)
    print(response.text)