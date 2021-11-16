# Weather Forecast Backend Api

Simple weather api created as a recruitment task. 
It enables user to fetch current weather data for chosen city from two external APIs: 
OpenWeatherMap and WeatherBit. OpenWeatherMap provides data for more then 200 000 cities, while WeatherBit for over 40 000. The sole GET endpoint takes three String parameters: 
city name (mandatory), external api name (not required) and units (not required). When no 
external api name is provided (WEATHER_BIT and OPEN_WEATHER_MAP are the options, underscores are necessary), the endpoint will return data from both APIs. If units 
parameter is not included, metric units are used (set as a default). Another possible options are SCIENTIFIC and IMPERIAL units. 
## Table of contents
* [Technologies](#technologies)
* [Setup](#setup)
* [Response](#response)
* [Example](#Example)

### Technologies
* Java SE 11
* Spring Boot Web
* Spring Boot Devtools
* JUnit
* AssertJ
* Lombok

### Setup
Download code to your IDE and run main method in WeatherForecastBackendApplication class:
```
public static void main(String[] args) {
		SpringApplication.run(WeatherForecastBackendApplication.class, args);
	}
```
The API launches on localhost:8080. Then you can call endpoint *getForecastFromChosenProviders()* with three parameters:
```
 public List<Forecast> getForecastFromChosenProviders(@RequestParam(name = "city") String city, 
                                                      @RequestParam(name = "api", required = false) String chosenApi, 
                                                      @RequestParam(name = "units", required = false) String units)
```
### Response
*getForecastFromChosenProviders()* endpoint returns list of Forecast objects. Each Forecast object contains fields describing various weather conditions:

String[] description - short description of most distinct feature of weather;

double pressure -  atmospheric pressure (hPa as default);;

double windSpeed - wind speed (m/s as default);

int windDirection - wind direction in degrees;

double temperature - temperature (Celsius degrees as default);

double apparentTemperature - feel-like temperature, taking humidity and wind into consideration (Celsius degrees as default);

int clouds - cloud cover in %;

double humidity - relative humidity in %;

double precipitation - amount of precipitation, measured for 1h (in millimeters as default);

double snow - amount of snow, measured for 1h (in millimeters as default);

Each Forecast contains also additional information:

String provider - name of API, from which date were fetched;

LocalDateTime lastObservationTime - time of observation (in your local timezone, not the place of observation);

String cityName - name of the city for which the forecast was done;

### Example
Sending a GET request to locally launched API with parameters:

city: Warsaw;

external api: OPEN_WEATHER_MAP

units: IMPERIAL
```
http://localhost:8080/getForecast?city=Warsaw&api=Open_weather_MAP&units=imperial
```
Response:
```
[{"provider":"OpenWeatherMap",
"lastObservationTime":"2021-11-16T20:26:40",
"description":["mist"],
"cityName":"Warsaw",
"pressure":1021.0,
"windSpeed":4.61,
"windDirection":130,
"temperature":39.09,
"apparentTemperature":35.73,
"clouds":0,
"humidity":91.0,
"precipitation":0.0,
"snow":0.0}]
```
Get request to WeatherBit API with SCIENTIFIC units:
```
http://localhost:8080/getForecast?city=Warsaw&api=WEATHER_BIT&units=scientific
```
Response
```
[{"provider":"WeatherBit",
"lastObservationTime":"2021-11-16T20:28:53",
"description":["Scattered Clouds"],
"cityName":"Warsaw",
"pressure":1012.99,
"windSpeed":1.98864,
"windDirection":147,
"temperature":276.0,
"apparentTemperature":271.7,
"clouds":40,
"humidity":96.3678,
"precipitation":0.0,
"snow":0.0}]
```
Get request to both APIs with default (metric) units:
```
[{"provider":"WeatherBit",
"lastObservationTime":"2021-11-16T20:28:43",
"description":["Scattered Clouds"],
"cityName":"Warsaw",
"pressure":1012.99,
"windSpeed":1.98864,
"windDirection":147,
"temperature":2.8,
"apparentTemperature":-1.4,
"clouds":40,
"humidity":96.3678,
"precipitation":0.0,
"snow":0.0},

{"provider":"OpenWeatherMap",
"lastObservationTime":"2021-11-16T20:40:04",
"description":["fog","mist"],
"cityName":"Warsaw",
"pressure":1021.0,
"windSpeed":0.45,
"windDirection":88,
"temperature":3.83,
"apparentTemperature":3.83,
"clouds":0,
"humidity":91.0,
"precipitation":0.0,
"snow":0.0}]
```