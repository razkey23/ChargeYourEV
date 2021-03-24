import click, requests, json, csv, os
from datetime import datetime
import urllib3
urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)

baseURL = 'https://localhost:8765/evcharge/api'
csvpath='C:/Users/razkey/Desktop/'
source="Sessionsmysqlworkbench.csv"

def checklogin():
    response="Running Login Test"+"\n"
    url = baseURL + '/login'
    username="admin"
    password="petrol4ever"
    data = {
        'username' : username,
        'password' : password
    }
    p=requests.post(url,data = data,verify=False)
    if(p.status_code==200):
        x=p.json()
        token="Bearer "+str(x['token'])
        if token == "Bearer ":
            response += "Login Failed"
        else:
            response +="Login Works"
    else:
        response="Login Failed"
    return response+"\n"+"***********************"+"\n",token


def checkTokenValidity(token):
    users="admin"
    url = baseURL + '/admin/users/'+users
    head = {'X-OBSERVATORY-AUTH': token}
    g = requests.get(url,headers=head,verify=False)
    response="Running  Token Validation Test"+"\n"
    if (g.status_code == 403):
        response += "Token Validity Failed"
    else:
        response += "Token Validation Works"
    return response+"\n"+"***********************"+"\n"

def checkHealthCheck():
    url = baseURL + '/admin/healthcheck'
    g = requests.get(url,verify=False)
    response="Running  HealthCheck Test"+"\n"
    if (g.status_code ==200):
        response+="Healthcheck works"
    else:
        response+="HealthCheck Failed"
    return response+"\n"+"***********************"+"\n"



def checkresetsession(token) :
    url = baseURL + '/admin/resetsessions'
    g=requests.post(url,verify=False)
    response="Running  Resetsession Test"+"\n"
    if (g.status_code == 200):
        response += "Resetsession Unit works"+"\n"
    url = baseURL + '/SessionsPerProvider/' + "1" + '/' + "20180101" + '/' + "20200101"
    head = {'X-OBSERVATORY-AUTH': token}
    format="json"
    par={'format':format}
    g = requests.get(url, headers = head,params=par,verify=False)
    if g.status_code==204:
        response+="Resetsession Functionality Works"
    else:
        response+="Resetsession Functionality Failed"
    return response+"\n"+"***********************"+"\n"


def checksessionupd(token):
    head={'X-OBSERVATORY-AUTH':token}
    response="Running session update test"+"\n"
    if os.path.exists(csvpath+source):
        csv=csvpath+source
        url = baseURL + '/admin/system/sessionupd'
        myfiles = {"file":(source,open(csv,"rb"),'application-type')}
        r = requests.post(url,headers=head, files=myfiles,verify=False)
        if r.status_code==200:
            response+="Sessionupdate Unit Works"+"\n"

        url = baseURL + '/SessionsPerProvider/' + "1" + '/' + "20180101" + '/' + "20200101"
        head = {'X-OBSERVATORY-AUTH': token}
        format="json"
        par={'format':format}
        g = requests.get(url, headers = head,params=par,verify=False)
        if g.status_code==204:
            response+="Sessionupdate Functionality Doesn't Work"
        else:
            response+="SessionUpdate Functionality Works "
        return response+"\n"+"***********************"+"\n"

def checkEndPoints(token):

    head = {'X-OBSERVATORY-AUTH': token}
    format="json"
    par={'format':format}
    response=""

    response+="\n"
    response+="***********************"+"\n"
    response+="Now Testing the EndPoint : /SessionsPerProvider/id/date_from/date_to"+"\n"
    ''' Sessions Per Provider UNIT AND FUNCTIONAL EXTERNAL TESTING '''

    url = baseURL + '/SessionsPerProvider/' + "1" + '/' + "20180101" + '/' + "20200101"
    g = requests.get(url, headers = head,params=par,verify=False)
    if g.status_code==200 or g.status_code==204:
        response+="SessionsPerProvider Unit Works"+"\n"
        y=g.json()
        with open("SessionsPerProviderResponse.json",'r',encoding="utf-8") as infile:
            jsonresponse=json.load(infile)
            #jsonresponse = infile.read()
            infile.close()
        for x in jsonresponse:
            del x['sessionID']
        for element in y :
            del element['sessionID']
        if y==jsonresponse:
            response+="SessionsPerProvider Functionality seems to be working"+"\n"
        else:
            response+="SessionsPerProviderFunctionality doesn't work"+"\n"
    else:
        response+="SessionsPerProvider Unit Failed"+"\n"



    response+="\n"
    response+="***********************"+"\n"
    response+="Now Testing the EndPoint : /SessionsPerEV/id/date_from/date_to"+"\n"
    ''' Sessions Per EV UNIT AND FUNCTIONAL EXTERNAL TESTING '''
    #2nd Functionality

    url = baseURL + '/SessionsPerEV/' + "42" + '/' + "20180101" + '/' + "20200101"
    g = requests.get(url, headers = head,params=par,verify=False)
    if g.status_code==200 or g.status_code==204:
        response+="SessionsPerEV Unit Works"+"\n"
        y=g.json()
        with open("SessionsPerEVResponse.json",'r',encoding="utf-8") as infile:
            jsonresponse=json.load(infile)
            #jsonresponse = infile.read()
            infile.close()
        temp1=[]
        temp2=[]
        z1=""
        z=""
        for x in jsonresponse['vehicleChargingSessionList']:
            del x['sessionID']
            z1=json.dumps(x,sort_keys=True)
            temp1.append(z1)

        lista=y['vehicleChargingSessionList']
        for element in y['vehicleChargingSessionList'] :
            del element['sessionID']
            z=json.dumps(element,sort_keys=True)
            temp2.append(z)
        if temp1==temp2:
            del jsonresponse['vehicleChargingSessionList']
            del y['vehicleChargingSessionList']
            del jsonresponse['requestTimestamp']
            del y['requestTimestamp']
            jsonresponse=json.dumps(jsonresponse,sort_keys=True)
            y=json.dumps(y,sort_keys=True)
            if jsonresponse==y:
                response+="SessionsPerEV Functionality seems to be working"+"\n"
            else:
                response+="SessionsPerEV Functionality doesn't work"+"\n"
        else:
            response+="SessionsPerEV Functionality doesn't work"+"\n"

        #Hard-coded Response from SQL
    else:
        response+="SessionsPerEV Unit Failed"+"\n"


    response+="\n"
    response+="***********************"+"\n"
    response+="Now Testing the EndPoint : /SessionsPerStation/id/date_from/date_to"+"\n"
    ''' Sessions Per Station UNIT AND FUNCTIONAL EXTERNAL TESTING '''

    url = baseURL + '/SessionsPerStation/' + "43" + '/' +"20180101" + '/' + "20200101"
    g = requests.get(url, headers = head,params=par,verify=False)
    if g.status_code==200 or g.status_code==204:
        response+="SessionsPerStations Unit Works"+"\n"
        y=g.json()
        with open("SessionsPerStationResponse.json",'r',encoding="utf-8") as infile:
            jsonresponse=json.load(infile)
            infile.close()
        temp1=[]
        temp2=[]
        z1=""
        z=""
        for x in jsonresponse['sessionsSummary']:
            z1=json.dumps(x,sort_keys=True)
            temp1.append(z1)

        for element in y['sessionsSummary'] :
            z=json.dumps(element,sort_keys=True)
            temp2.append(z)
        if temp1==temp2:

            del jsonresponse['sessionsSummary']
            del y['sessionsSummary']
            del jsonresponse['requestTimestamp']
            del y['requestTimestamp']
            jsonresponse=json.dumps(jsonresponse,sort_keys=True)
            y=json.dumps(y,sort_keys=True)
            if jsonresponse==y:
                response+="SessionsPerStation Functionality seems to be working"+"\n"
            else:
                response+="SessionsPerStation Functionality doesn't work"+"\n"
        else:
            response+="SessionsPerStation Functionality doesn't work"+"\n"
    else:
        response+="SessionsPerStation Unit Failed"+"\n"


    response+="\n"
    response+="***********************"+"\n"
    response+="Now Testing the EndPoint : /SessionsPerPoint/id/date_from/date_to"+"\n"


    url = baseURL + '/SessionsPerPoint/' + "120" + '/' +"20180101" + '/' + "20200101"
    g = requests.get(url, headers = head,params=par,verify=False)

    if g.status_code==200 or g.status_code==204:
        response+="SessionsPerPoint Unit Works"+"\n"
        y=g.json()
        with open("SessionsPerPointResponse.json",'r',encoding="utf-8") as infile:
            jsonresponse=json.load(infile)
            infile.close()
            temp1=[]
            temp2=[]
            z1=""
            z=""
            for x in jsonresponse['chargingSessionsList']:
                del x['sessionID']
                z1=json.dumps(x,sort_keys=True)
                temp1.append(z1)
            for element in y['chargingSessionsList'] :
                del element['sessionID']
                z=json.dumps(element,sort_keys=True)
                temp2.append(z)
            if temp1==temp2:
                del jsonresponse['chargingSessionsList']
                del y['chargingSessionsList']
                del jsonresponse['requestTimestamp']
                del y['requestTimestamp']
                jsonresponse=json.dumps(jsonresponse,sort_keys=True)
                y=json.dumps(y,sort_keys=True)
                if jsonresponse==y:
                    response+="SessionsPerPoint Functionality seems to be working"+"\n"
                else:
                    response+="SessionsPerPoint Functionality doesn't work"+"\n"
            else:
                response+="SessionsPerPoint Functionality doesn't work"+"\n"
    else:
        response+="SessionsPerPoint Unit Failed"+"\n"




    return response

#Main is Here
print(" RUNNING THE FOLLOWING TESTS")
print(" ****************************************************************")
print(" ****************************************************************")
print(" ****************************************************************")
print(" ****************************************************************")
print(" ****************************************************************")
print(" ****************************************************************")
print(" ****************************************************************")
LoginResponse,token = checklogin()
print(LoginResponse)
TokenValidityResponse = checkTokenValidity(token)
print(TokenValidityResponse)
HealthCheckResponse = checkHealthCheck()
print(HealthCheckResponse)
resetsessionresponse = checkresetsession(token)
print(resetsessionresponse)
sessionupdresponse = checksessionupd(token)
print(sessionupdresponse)
x=checkEndPoints(token)
print(x)
