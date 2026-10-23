import urllib.request
import json

# 1. Put your details here
owner = "facebook"
repo = "react"

# 2. Build the GitHub API URL
url = f"https://github.com{owner}/{repo}"

try:
    # 3. Make the request to GitHub (adding a User-Agent header is required by GitHub)
    req = urllib.request.Request(url, headers={'User-Agent': 'Python-Script'})
    
    with urllib.request.urlopen(req) as response:
        # 4. Parse the text data into Python format
        data = json.loads(response.read().decode())
        
        # 5. Print the results out clearly
        print("--- GitHub Repository Details ---")
        print(f"Owner Name:    {data['owner']['login']}")
        print(f"Repo Name:     {data['name']}")
        print(f"GitHub ID:     {data['id']}  <-- Paste this into your form!")
        print(f"Default Branch: {data['default_branch']}")

except Exception as e:
    print(f"Error fetching data: {e}")
    print("Please check that the owner and repository names are spelled correctly.")
