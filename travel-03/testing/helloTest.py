import requests

url = 'http://localhost:9090/hello'
response = requests.get(url)

if response.status_code == 200:
    print(f"Response status code: {response.status_code}")
    print(f"Response content: {response.text}")
else:
    print(f"Error accessing {url}. Status code: {response.status_code}")