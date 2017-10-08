# weather-app

An android application used to inform the user about the current weather conditions in his current location.

## Core features

* Support for different forecast resolution, namely hourly (every 3h) and daily (1 data point per day)
* Support for hourly forecasts up to 5 days in the future 
* Support for daily forecasts up to 5 days in the future
* In-depth information about a forecast
* Ability to find the current location of the user
* Support for location input and forecast feedback for the chosen location
* Proper error handling in the form of text and visual graphics
* Support for notifications (only for hourly resolution)

## Design

The design is made by following these guide lines, [Android Design](https://developer.android.com/design/index.html).

* [Splash screen](https://user-images.githubusercontent.com/29614239/31318215-52e26a64-ac57-11e7-9704-d9b0a8c6fc69.jpg)
* [Hourly forecast screen](https://user-images.githubusercontent.com/29614239/31318218-5cd76e34-ac57-11e7-9363-aa02e237a59c.jpg)
* [Daily forecast scree](https://user-images.githubusercontent.com/29614239/31318220-6235f512-ac57-11e7-8ff8-fd43faa2dec6.jpg)
* [Settings screen](https://user-images.githubusercontent.com/29614239/31318222-68ac8384-ac57-11e7-8a0f-9d6586f7e15d.jpg)
* [Search screen](https://user-images.githubusercontent.com/29614239/31318224-6fced324-ac57-11e7-8acf-fc9df7413460.jpg)
* [Search screen with results](https://user-images.githubusercontent.com/29614239/31318226-797cb724-ac57-11e7-8594-460244baeabf.jpg)
* [Search screen with no results](https://user-images.githubusercontent.com/29614239/31318227-7cac556c-ac57-11e7-86a1-eba003a7dd1d.jpg)

## Dependencies

* [Mosby MVP](http://hannesdorfmann.com/mosby/)
* [Retrofit](http://square.github.io/retrofit/)
* [Joda-Time](http://www.joda.org/joda-time/)
* [Glide](http://bumptech.github.io/glide/)
* [GreenDAO](http://greenrobot.org/greendao/)
* [Google Location Services](https://developer.android.com/training/location/index.html)

## Credits

* [MetaWeather API](https://www.metaweather.com/)
* [App icon by Alfredo Hernandez](https://www.flaticon.com/authors/alfredo-hernandez)

## License

   Copyright 2017 Petar Krastev

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       ![](http://www.apache.org/licenses/LICENSE-2.0)

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
