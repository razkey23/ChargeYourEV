import click, requests, json, csv, os
from datetime import datetime
from click_option_group import optgroup, RequiredMutuallyExclusiveOptionGroup


baseURL = 'https://localhost:8765/evcharge/api'
tokenPATH = './'
csvpath='C:/Users/razkey/Desktop/'
tokenNAME = 'softeng20bAPI.token'

class bcolors:
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'



def adminParamCheck(usermod,username,passw,users,sessionupd,source,healthcheck,resetsessions):
    if usermod:
        if sessionupd or healthcheck or users or resetsession or source :
            return True
        return False
    elif users:
        if usermod or username or passw or sessionupd or source or healthcheck or resetsessions:
            return True
        return False
    elif sessionupd:
        if usermod or username or passw or users or healthcheck or resetsessions:
            return True
        return False
    elif healthcheck:
        if usermod or username or passw or users or sessionupd or source or resetsessions :
            return True
        return False
    elif resetsessions:
        if usermod or username or passw or users or source or sessionupd or source or healthcheck:
            return True
        return False
    else:
        return True

def isPotentialDate(x):
    if len(x)==8:
        print(len(x))
        if x.isnumeric():
            print(x.isnumeric())
            return True
    else:
        return False

@click.group()
def ev_group41():
    pass

@ev_group41.command()
@click.option('--username', required=True, type = str)
@click.option('--password',required=True, type = str)
def login(username,password):
    url = baseURL + '/login'
    data = {
        'username' : username,
        'password' : password
    }
    p=requests.post(url,data = data,verify=False)
    if(p.status_code==200):
        click.echo(f'Υou have logged in successfully.')
        x = p.json()
        click.echo(str(x['token']))
        with open(tokenPATH + tokenNAME, 'w') as outfile:
            outfile.write("Bearer "+str(x['token']))
            outfile.close()
    else:
        click.echo(f'Log in error.')


@ev_group41.command()
def logout():
    url=baseURL+ '/logout'
    with open(tokenPATH+tokenNAME,'rb') as infile:
        token = infile.read()
        infile.close()

    head={'X-OBSERVATORY-AUTH':token}
    p = requests.post(url,headers=head,verify=False)
    if(p.status_code==200):
        if os.path.exists(tokenPATH + tokenNAME):
            os.remove(tokenNAME)
            click.echo("You have logged out successfully.")
    else:
        click.echo("Error logging out.")

@ev_group41.command()
@click.option('--point',required=True,type=str)
@click.option('--datefrom',required=True,type=str)
@click.option('--dateto',required=True,type=str)
@click.option('--format',type=click.Choice(['csv', 'json'], case_sensitive=True))
#@click.option('--apikey',required=True,type=str)
def SessionsPerPoint(point,datefrom,dateto,format):
    url = baseURL + '/SessionsPerPoint/' + point + '/' + datefrom + '/' + dateto
    if isPotentialDate(dateto)==False or isPotentialDate(datefrom)==False:
         click.echo("Wrong date Input" +bcolors.ENDC)
         return
    with open(tokenPATH + tokenNAME ,'r') as infile:
        token = infile.read()
        infile.close()
        click.echo(token)
    head = {'X-OBSERVATORY-AUTH': token}
    par={'format':format}
    g = requests.get(url, headers = head,params=par,verify=False)
    if(g.status_code == 402):
        click.echo(f'Error. You are out of quota.')
    elif(g.status_code == 403):
        click.echo(f'Error. There is no such data.')
    elif(g.status_code == 404):
        click.echo(f'Error. Bad request.')
    else:
        #y=g.json()
        if (format=="json") :
            y=g.json()
            #click.echo(g.content)
            click.echo(json.dumps(y,indent=4,sort_keys=True))
        else:
            #y=g.csv()
            click.echo(g.content)

        #click.echo(y['result'])

@ev_group41.command()
@click.option('--station',required=True,type=str)
@click.option('--datefrom',required=True,type=str)
@click.option('--dateto',required=True,type=str)
@click.option('--format',type=click.Choice(['csv', 'json'], case_sensitive=True))
#@click.option('--apikey',required=True,type=str)
def SessionsPerStation(station,datefrom,dateto,format):
    url = baseURL + '/SessionsPerStation/' + station + '/' + datefrom + '/' + dateto
    if isPotentialDate(dateto)==False or isPotentialDate(datefrom)==False:
         click.echo("Wrong date Input" +bcolors.ENDC)
         return
    with open(tokenPATH + tokenNAME ,'r') as infile:
        token = infile.read()
        infile.close()
        click.echo(token)
    head = {'X-OBSERVATORY-AUTH': token}
    par={'format':format}
    g = requests.get(url, headers = head,params=par,verify=False)
    if(g.status_code == 402):
        click.echo(f'Error. You are out of quota.')
    elif(g.status_code == 403):
        click.echo(f'Error. There is no such data.')
    elif(g.status_code == 404):
        click.echo(f'Error. Bad request.')
    else:
        #y=g.json()
        if (format=="json") :
            y=g.json()
            click.echo(json.dumps(y,indent=4,sort_keys=True))
        else:
            #y=g.csv()
            click.echo(g.content)

@ev_group41.command()
@click.option('--ev',required=True,type=str)
@click.option('--datefrom',required=True,type=str)
@click.option('--dateto',required=True,type=str)
@click.option('--format',type=click.Choice(['csv', 'json'], case_sensitive=True))
#@click.option('--apikey',required=True,type=str)
def SessionsPerEV(ev,datefrom,dateto,format):
    url = baseURL + '/SessionsPerEV/' + ev + '/' + datefrom + '/' + dateto
    if isPotentialDate(dateto)==False or isPotentialDate(datefrom)==False:
         click.echo("Wrong date Input" +bcolors.ENDC)
         return
    with open(tokenPATH + tokenNAME ,'r') as infile:
        token = infile.read()
        infile.close()
        click.echo(token)
    head = {'X-OBSERVATORY-AUTH': token}
    par={'format':format}
    g = requests.get(url, headers = head,params=par,verify=False)
    if(g.status_code == 402):
        click.echo(f'Error. You are out of quota.')
    elif(g.status_code == 403):
        click.echo(f'Error. There is no such data.')
    elif(g.status_code == 404):
        click.echo(f'Error. Bad request.')
    else:
        #y=g.json()
        if (format=="json") :
            y=g.json()
            click.echo(json.dumps(y,indent=4,sort_keys=True))
        else:
            #y=g.csv()
            click.echo(g.content)



@ev_group41.command()
@click.option('--provider',required=True,type=str)
@click.option('--datefrom',required=True,type=str)
@click.option('--dateto',required=True,type=str)
@click.option('--format',type=click.Choice(['csv', 'json'], case_sensitive=True))
#@click.option('--apikey',required=True,type=str)
def SessionsPerProvider(provider,datefrom,dateto,format):
    url = baseURL + '/SessionsPerProvider/' + provider + '/' + datefrom + '/' + dateto
    if isPotentialDate(dateto)==False or isPotentialDate(datefrom)==False:
         click.echo("Wrong date Input" +bcolors.ENDC)
         return
    with open(tokenPATH + tokenNAME ,'r') as infile:
        token = infile.read()
        infile.close()
        click.echo(token)
    head = {'X-OBSERVATORY-AUTH': token}
    par={'format':format}
    g = requests.get(url, headers = head,params=par,verify=False)
    if(g.status_code == 402):
        click.echo(f'Error. You are out of quota.')
    elif(g.status_code == 403):
        click.echo(f'Error. There is no such data.')
    elif(g.status_code == 404):
        click.echo(f'Error. Bad request.')
    else:
        #y=g.json()
        if (format=="json") :
            y=g.json()
            click.echo(json.dumps(y,indent=4,sort_keys=True))
        else:
            #y=g.csv()
            click.echo(g.content)


@ev_group41.command()
def healthcheck():
    url = baseURL + '/admin/healthcheck'
    g = requests.get(url,verify=False)
    if(g.status_code == 402):
        click.echo(f'Error. You are out of quota.')
    elif(g.status_code == 403):
        click.echo(f'Error. There is no such data.')
    elif(g.status_code == 404):
        click.echo(f'Error. Bad request.')
    else:
        y=g.json()
        click.echo(y['status'])
        #y=g.json()
        #print(y.content)


@ev_group41.command()
def resetsession():
    url = baseURL + '/admin/resetsessions'
    g=requests.get(url,verify=False)
    if(g.status_code == 402):
        click.echo(f'Error. You are out of quota.')
    elif(g.status_code == 403):
        click.echo(f'Error. There is no such data.')
    elif(g.status_code == 404):
        click.echo(f'Error. Bad request.')
    else:
        y=g.json()
        click.echo(y['status'])



@ev_group41.command()
@click.option('--usermod',required=False,is_flag=False)
@click.option('--username',required=False,type=str)
@click.option('--passw',required=False,type=str)
@click.option('--users',required=False,type=str)
@click.option('--sessionupd',required=False,is_flag=False)
@click.option('--source',required=False,type=str)
@click.option('--healthcheck',required=False,is_flag=False)
@click.option('--resetsessions',required=False,is_flag=False)
def Admin(usermod,username,passw,users,sessionupd,source,healthcheck,resetsessions):
    x = adminParamCheck(usermod,username,passw,users,sessionupd,source,healthcheck,resetsessions)
    if x :
        click.echo("Conflicts in Parameter Passing" +bcolors.ENDC)
        return
    with open(tokenPATH + tokenNAME ,'r') as infile:
        token = infile.read()
        infile.close()
        #click.echo(token)
    head = {'X-OBSERVATORY-AUTH': token}
    if usermod:
        if username!=None and passw!=None:
            url = baseURL + '/admin/usermod/'+username+'/'+passw
            g = requests.post(url,headers = head,verify=False)
            url = baseURL + '/login'
            data = {
                'username' : username,
                'password' : passw
            }
            p=requests.post(url,data = data,verify=False)
            x = p.json()
            click.echo(str(x['token']))

    elif users:
        url = baseURL + '/admin/users/'+users
        g = requests.get(url,headers=head,verify=False)
        y= g.json()
        password=y['password']
        click.echo(g.content)

    elif sessionupd:
        if source:
            if os.path.exists(csvpath+source):
                csv=csvpath+source
                url = baseURL + '/admin/system/sessionupd'
                myfiles = {"file":(source,open(csv,"rb"),'application-type')}
                r = requests.post(url,headers=head, files=myfiles,verify=False)
                print(r.json())
    elif resetsessions:
        url= baseURL+'/admin/resetsessions'
        g = requests.post(url,headers=head,verify=False)
        y=g.json()
        click.echo(y['status'])

    elif healthcheck:
        url= baseURL +'/admin/healthcheck'
        g = requests.get(url,headers=head,verify=False)
        print(json.dumps(json.loads(g.text), indent=4))


def main():
    ev_group41()

if __name__ == '__main__':
    main()
