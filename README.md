# Firebase Real Time Database Crud

#### Project setup
**Step 1:** First create a project.

**Step 2:** Then go to [Firebase](https://firebase.google.com/) and login by your gmail and create a new project.
* Give project name
* Select your country
* Check accept and press create
* Then Select android icon from top nav
* Then copy your project name from appliction and paste it first box from firebase console
* You can give Debug signing certificate SHA-1 but it is optional . For creating certificate from your android studio click gradle from right side of screen , then click android > 	  singingReport . then see your console u found a sha-1 fingerprint . copy it and paste it in firbase requird field.

**Step 3:** Download **google-services.json** file and paste it on app folder (select project view first)

**Step 4:**
* Add dependency in project label -``` classpath 'com.google.gms:google-services:4.0.1' ```
* Add depency in app label - 
```gradle 
 	* implementation 'com.google.firebase:firebase-core:16.0.1'
    * implementation 'com.google.firebase:firebase-database:16.0.1'
    * implementation 'com.google.firebase:firebase-auth:16.0.1'
```
* If u have some support error then add dependency ``` implementation 'com.android.support:support-v4:28.0.0' ```
*  Appy pluging in app lavel bottom of the page ``` apply plugin: 'com.google.gms.google-services' ```
 
**Step 5:** Then sync the project and run it then got to console and see verify installation success or not (bottom)

**Step 6:** Click on database from develop section in firebase console and create realtime database. and enabled it test mode.

**Step 6:** Give a scema name your database and insert some data (no sql); and click add.

**Step 7:** see FirbaseDBConfig.class 

### For Authentication:

	1. from firebase console go to database -> roles and chand json file like -
	
    	```json
    	{
  		 	"rules": {
    			".read": true,
    			".write": "auth != null"
  			}
		}
		```
        
	2. Click on publish
    3. see SignInActivity.class for creating user and login
    4. For get instence - 
    	1. FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        2. FirebaseUser firebaseUser = firebaseAuth.getCurrentUser(); 	
    

# Fabric Crashlytics for Android

1. First Create or login in [Fabric](https://get.fabric.io/)
2. Add dependency in app lavel in gradle file

```gradle
buildscript {
  repositories {
    maven { url 'https://maven.fabric.io/public' }
  }

  dependencies {
   
    // We recommend changing it to the latest version from our changelog:
    // https://docs.fabric.io/android/changelog.html#fabric-gradle-plugin
    classpath 'io.fabric.tools:gradle:1.+'
  }
}

apply plugin: 'io.fabric'

repositories {
  maven { url 'https://maven.fabric.io/public' }
}

dependencies {
	implementation 'com.crashlytics.sdk.android:crashlytics:2.9.8'
}
```

Or you can do it by fabric plugin in android studio

#### For apply by plugin - 

	* Go to Files-> Settings ->Plugins
	* Search by febric. if you doesnt find anything click on Search on repositories
	* if everything is fine you will see an option for febric in right side on android studio.
	* click on febric option and login by febric account
	* Choose Crashlytics from all kits
	* Click on Crashlytics install.
	* Then Click on apply.
	* Then sync gradle and rebuild your Application
	
 **Note: ** You will see all crush report on febric console.