# Banking-App-Mock-Compose

**Banking-App-Mock-Compose** is an android app written with **Jetpack Compose**

The primary purpose of the app is to practice layout of medium-size app in Compose instead of Android Views. 

The details of all other layers except ui may be missing or be simplified. 
For example, for banking profile the app uses local mock Repositories with static data with no remote datasource.

**Design reference**: https://www.figma.com/community/file/1106055934725589367/Finance-Mobile-App-UI-KIT

### Stack
- Jetpack Compose
- Koin
- Ksprefs
- Jetpack Navigation

### Gallery
1. Onboarding, Sign in and user profile
<p align="left">
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/f6436798-c655-45fd-940f-909108f0cd8f" width="20%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/9af32fed-6bdd-421a-bbad-b0a6b30c9384" width="20%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/5b60ccfd-c1a5-4716-a18b-d70c434f6ea1" width="20%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/498d3dcc-a79f-4575-8bf5-bf678777683a" width="20%"/>
</p>

2. Cards and Savings
<p align="left">
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/aacaeac3-24f7-491d-a69f-604d06d8de5a" width="20%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/bbb1c8ad-e1cd-4958-9da1-b4c32c88f67a" width="20%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/edee3a09-940a-4c18-b8c6-8ceec1c017fb" width="20%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/7b25851e-68ef-416e-bbdc-d37e67156f43" width="20%"/>
</p>

### Implemented features
- [ ] App core:
    - [X] Splash screen
    - [X] Navigation
    - [X] Operation handling core (OperationResult class)
    - [X] Popup messages (snackbar)
    - [X] Error UI
    - [ ] App lock (PIN)
    - [ ] App lock (biometrics)
    - [ ] Data enrcryption
- [X] Onboarding slider
- [X] Sign up with email
- [ ] Sign up with phone number
- [ ] Restore password
- [X] OTP verification
- [ ] Home screen:
    - [X] Cards
    - [X] Savings
    - [X] Profile
    - [ ] Balance observable
    - [ ] Notifications observable
- [ ] Cards
    - [X] Card list
    - [X] Create card
    - [X] Delete carrd
    - [ ] Card Statistics
- [X] Savings
    - [X] Savings list
    - [X] Saving details
- [ ] Account operations
    - [ ] Send money
    - [ ] Request money
    - [ ] Top up
    - [ ] Pay
    - [ ] Transaction history
    - [ ] Transaction contacts
 - [ ] Profile
    - [X] Profile info
    - [ ] QR codes (scan, my QR)
    - [ ] Edit profile
- [ ] Account and security section
- [ ] Help section
- [ ] Notifications 
- [X] Logout
- [X] Terms and Conditions (WebView)
