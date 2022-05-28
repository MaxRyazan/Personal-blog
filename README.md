# web-site
Personal blog creating with Bootstrap. Minimum front features.  
Stack : Spring: Boot, MVC, Security. Bootstrap sample, Thymeleaf template engine, Html. Data Base - MySQL.  
Last update: Authorization and Registration was added.  
Last update: Roles, custom login/logout pages. A bit of javascript code. 
Next update: Admins table with users, roles and buttons.
Registration page:  
![реги](https://user-images.githubusercontent.com/97405800/170830268-51bfab88-d86a-42d3-af11-ff06859f928f.jpg)  
This is main page : 
![админблогглавная](https://user-images.githubusercontent.com/97405800/170830291-98c40603-5c8d-4079-88d9-df6db439bbc9.jpg))  
Add new post ? Click "Добавить статью" See this :  
![добавление статьи](https://user-images.githubusercontent.com/97405800/170830350-b23be12f-c833-480f-a532-ac9cf4ac1da9.jpg)  
Want to see more details and full-post-text? Click "Детальнее" :
![детальнее](https://user-images.githubusercontent.com/97405800/170830361-bb51fff8-15c1-467d-992b-9768251c36c3.jpg)
The user's role affects the displayed content:  
Guest see this:  
![шапка гость](https://user-images.githubusercontent.com/97405800/170830433-aef3f165-48da-4dc9-ab33-a6d4a714cb12.jpg)  
but auth user this:  
![шапка юзер](https://user-images.githubusercontent.com/97405800/170830481-76f9bcfe-6695-46b5-8ae9-1f79649b1829.jpg)  

  
Update archive:  
UPD:  
Added attributes to buttons (isAuthorize(), hasRole() etc) for more "user-friendly" interface.  
Added service class, extracted logic from controllers.  
Added some pages for convenience.  
Some visual fixes.  
UPD:  
Added enum Role, added guest enter. Optimized class structure.  
