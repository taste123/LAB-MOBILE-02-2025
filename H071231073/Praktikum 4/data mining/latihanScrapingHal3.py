import requests
from bs4 import BeautifulSoup

url = 'https://www.scrapethissite.com/pages/ajax-javascript/#2010'
requests.get(url)

soup = BeautifulSoup(requests.get(url).text, 'html.parser')

films = soup.find_all('tr', class_='film')
for i in films:
  print((i.find('td', class_='film-title').text).strip())
  print((i.find('td', class_='nominations').text).strip())