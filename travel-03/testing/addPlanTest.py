import requests

url = 'http://localhost:9090/'
address = 'location/query/故宫'
dest = url+address
response = requests.get(dest)

if response.status_code == 200:
    print(f"Response status code: {response.status_code}")
    print(f"Response content: {response.text}")
else:
    print(f"Error accessing {url}. Status code: {response.status_code}")