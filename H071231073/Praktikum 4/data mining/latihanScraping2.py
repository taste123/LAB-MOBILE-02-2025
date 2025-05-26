import requests
from bs4 import BeautifulSoup 

url = 'https://www.scrapethissite.com/pages/simple/'
requests.get(url)
# print(requests.get(url).text)

soup = BeautifulSoup(requests.get(url).text, 'html.parser')

nama = soup.find('h3', class_='country-name')
capital =  soup.find('span', class_='country-capital')
population =  soup.find('span', class_='country-population')
area =  soup.find('span', class_='country-area')

soup1 = soup.find_all('div', class_='country')

names, capitals, populations, areas = [], [], [], []
# print(soup1)
for i in soup1:
    print((i.find('h3', class_='country-name').text).strip())
    names.append((i.find('h3', class_='country-name').text).strip())

    print(i.find('span', class_='country-capital').text)
    capitals.append(i.find('span', class_='country-capital').text)

    print(i.find('span', class_='country-population').text)
    populations.append(i.find('span', class_='country-population').text)

    print(i.find('span', class_='country-area').text)
    areas.append(i.find('span', class_='country-area').text)

    print('-'*40)