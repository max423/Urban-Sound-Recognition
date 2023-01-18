# GESTIONE UTENTI
import pandas as pd
import requests as req

def import_data(num):
    """Function that make a request to the server to load a {num} of users."""

    CONST_FIELDS = "gender,login,email,name,dob,picture"
    payloads = {"results": num, "inc": CONST_FIELDS}

    df = pd.DataFrame(columns=('username', 'email', 'password', 'firstName', 'lastName', 'picture'))
    r = req.get("https://randomuser.me/api/", params=payloads).json()

    for result in r['results']:
        user = {'username': result['login']['username'],
                'email': result['email'],
                'password': result['login']['password'],
                'firstName': result['name']['first'],
                'lastName': result['name']['last']
                }
        df = df.append(user,  ignore_index=True)

    data = df.drop_duplicates(subset=["username"], keep="first")

    path = "./data/users.json"
    data.to_json(path, orient='records', lines=True)
    print("Saved Json file for " + num + " users")

    return path

if __name__ == "__main__":
    num = input("Number of dummy users:\n")
    import_data(num)