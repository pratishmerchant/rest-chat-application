# How to install rest-chat-app :
__Step 1:__ Clone the repository from GitHub

  ```cmd
  $ git clone https://github.com/pratishmerchant/rest-chat-application.git
  ```

__Step 2:__ Update the properties file

  * Navigate to the _resources_ folder and edit application.properties

	```cmd
	$ cd src/main/resources
	```
  
__Step 3:__ Run the _maven_ build

  * Navigate to the build project directory 

  * Build projects using maven as follows:

    ```cmd
    $ mvn -e clean install
    ```
    Maven log should report a build success for all the component projects.
  
__Step 4:__ Run on localhost
  
   * Use the following command :
   ```cmd
   http://<ip-or-localhost>:8080/<end-point>
   ```
   
 ## Documentation :
 Complete documentation can found under `doc/index.html`
 
 ## SQL DUMPS : 
 Located in folder `src\main\resources`. (Create a db if required)
