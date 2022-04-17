# juara-android

Juara-Android Indonesia 2022 [link](https://gdg.community.dev/events/details/google-gdg-jakarta-presents-info-session-juaraandroid-season-1/)  
**Google Developer Profile**: [natashaval](https://developers.google.com/profile/u/natashaval)  
Codelabs Training: [juara-android](https://github.com/natashaval/juara-android)  

## Project Demo
Number Trivia Project Demo  
![Number Trivia Demo](screenshots/number_trivia.mp4)  
Download the app here: [Google Drive](https://drive.google.com/drive/folders/1F95_fAqfvLtikyJLK8YpYs3XU_0x7oon?usp=sharing)
View the demo here: [Youtube](https://youtu.be/HploO0biq48)

## Overview
- Number Trivia is a simple Android application to show an interesting fact about numbers. The trivia is taken from NumbersApi.  
- Make sure the phone is connected to Internet to retrieve from the API  
![Number Fragment](screenshots/number_fragment_0.png)
- On opening the app, it shows fun fact from a random number
![Number Fragment Filled](screenshots/number_fragment.png)
- User fills out the input field and select the type
- User clicks on Generate button
- The app shows a fact for the requested number
- Clicking on the heart icon, the number is saved to Favorites
- Clicking again on the heart icon, the number is removed from Favorites
![Detail Fragment](screenshots/detail_fragment.png)
- User clicks on the numbers, and the app opens the number detail page
- Clicking the "Copy" button, the text is copied to device clipboard
- Clicking the "Send Trivia!" button, the text is opened in other app, such as Messages or Email
![Favorite Fragment](screenshots/favorite_fragment.png)
- The app shows a list of saved favorite numbers
- Clicking on one of the numbers, the number is opened in detail page

## Tech Stack
1. Android Navigation
2. ViewModel and LiveData
3. Retrofit
4. Room
5. Android Hilt dependency injection
6. Recycler View List Adapter
7. Implicit Intent

### Challenges
I faced a challenge to synchronize the fact retrieved from API and the fact fetched from the database.  
This issue is to decide the drawable for favorite icon (filled / outlined).  
The implementations that I applied when user clicks on the favorite icon:
1. Fetch the fact from the API
2. Retrieve the fact based on a specific number from database
3. Compare whether the fact already available or not in database
    - If the fact is not yet available in the database, insert the fact to database
    - If the fact is already available in the database, update the boolean is_favorite in database
4. Update the UI

## Credits
Credits to [Numbers API](http://numbersapi.com/) for providing the API  
Made with ♥ natashaval️