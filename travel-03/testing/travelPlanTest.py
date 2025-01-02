import requests
import json

class TravelPlan:
    def __init__(self, startLocation, endLocation):
        self.StartLocation = startLocation
        self.EndLocation = endLocation

# Create a new User object
travelPlan = TravelPlan("香山", "故宫")

# Convert the User object to a JSON payload
payload = json.dumps(travelPlan.__dict__)

# Set the headers
headers = {
    'Content-Type': 'application/json'
}

# Make the POST request to localhost:9090
response = requests.post("http://localhost:9090/travel/query", headers=headers, data=payload)

# Check the response
if response.status_code == 200:
    print("POST request successful!")
    print(response.text)
else:
    print("POST request failed with status code:", response.status_code)
    print(response.text)