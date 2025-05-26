# print("hello world")
import requests
from bs4 import BeautifulSoup  

url = 'https://www.scrapethissite.com/pages/simple/'
requests.get(url)
# print(requests.get(url).text)

soup = BeautifulSoup(requests.get(url).text, 'html.parser')
# print(soup.find('h1').text)# print the parsed HTML

data = soup.find('h3', class_='country-name')
print(data.text)

capital =  soup.find('span', class_='country-capital')
print(capital.text)

population =  soup.find('span', class_='country-population')
print(population.text)

area =  soup.find('span', class_='country-area')
print(area.text)
