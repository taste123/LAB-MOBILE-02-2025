import requests
from bs4 import BeautifulSoup 

names, years, winss, losseses, OT_lossess, win_percentages, goalFors, goalAgainsts, differences = [], [], [], [], [], [], [], [], []
for i in range (1,25):
  url = f'https://www.scrapethissite.com/pages/forms/?page_num={i}'
  requests.get(url)

  soup = BeautifulSoup(requests.get(url).text, 'html.parser')

  teams = soup.find_all('tr', class_='team')
  for i in teams:
      print((i.find('td', class_='name').text).strip())
      names.append((i.find('td', class_='name').text).strip())

      print((i.find('td', class_='year').text).strip())
      years.append((i.find('td', class_='year').text).strip())

      print((i.find('td', class_='wins').text).strip())
      winss.append((i.find('td', class_='wins').text).strip())

      print((i.find('td', class_='losses').text).strip())
      losseses.append((i.find('td', class_='losses').text).strip())

      print((i.find('td', class_='ot-losses').text).strip())
      OT_lossess.append((i.find('td', class_='ot-losses').text).strip())

      win_percentage = i.find('td', class_='pct text-success')
      if win_percentage:
          print(win_percentage.text.strip())
          win_percentages.append(win_percentage.text.strip())
      else:
          print('N/A')
          win_percentages.append('N/A')

      print((i.find('td', class_='gf').text).strip())
      goalFors.append((i.find('td', class_='gf').text).strip())

      print((i.find('td', class_='ga').text).strip())
      goalAgainsts.append((i.find('td', class_='ga').text).strip())

      difference = i.find('td', class_='diff text-success')
      if difference:
          print(difference.text.strip())
          differences.append(difference.text.strip())
      else:
          print('N/A')
          differences.append('N/A')

      print('-'*30 + '\n')