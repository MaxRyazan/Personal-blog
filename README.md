# web_blog
Personal blog creating with Bootstrap, Html and Spring framework.
Stack :  
  Spring:  
    Boot, MVC, Security.  
      Bootstrap sample, Thymeleaf template engine, Html. Data Base - MySQL.  
THIS IS A FINAL VERSION.  
(archive)Last update: Admins table, added users STATUS (ACTIVE, BANNED). Dynamic button scope depending on user role.  
(archive)Last update: Roles, custom login/logout pages. A bit of javascript code.  
(archive)Last update: Authorization and Registration was added.  

Registration page:  
![регистрация1](https://user-images.githubusercontent.com/97405800/171958375-758a6e87-1ef4-424b-9ec9-20af7428355a.jpg)
![реги-ошибка](https://user-images.githubusercontent.com/97405800/171958390-e70d4e8c-ae94-4e84-946d-77a96a02509d.jpg)

Guest login allows you to view content without the ability to change or add anything.  
If you don't have an account, you can create one here:  
![новый ошибка](https://user-images.githubusercontent.com/97405800/171958679-727decc9-7166-4805-9f29-5abab490b52c.jpg)  

Now let's look at a site header: 
On the left you see the email of the current user, right buttons are different for different user roles:  
![header admin](https://user-images.githubusercontent.com/97405800/171959201-0dff2c2d-50f5-4e1b-8069-554bf4f2d1bc.jpg)
![header guest](https://user-images.githubusercontent.com/97405800/171959911-86529a03-2555-47e3-9b00-827bd0d297d6.jpg)
![header user](https://user-images.githubusercontent.com/97405800/171959206-ed78fbde-167f-4a26-8640-3b23f873498d.jpg)

Now let's check blog itself:  
Guest can only view content, USER can add and edit articles, ADMIN can additionally delete.  
![блог главная](https://user-images.githubusercontent.com/97405800/171960619-241b1967-aeed-4cb7-9d4d-25dac0b4e8f6.jpg)
![гость блок](https://user-images.githubusercontent.com/97405800/171960630-f836c30c-24bb-4cf2-a5c3-34289ac4920d.jpg)
![юзер блог](https://user-images.githubusercontent.com/97405800/171960634-4355c17b-5444-4874-896f-0eee8c6c625d.jpg)
![админ блог](https://user-images.githubusercontent.com/97405800/171960635-05830b4b-aa98-45c4-b19a-9c243fcdec61.jpg)

Admins content:  
![111](https://user-images.githubusercontent.com/97405800/171961300-e6c7b81b-f060-4cd1-8993-73e5424f3349.jpg)
![2222](https://user-images.githubusercontent.com/97405800/171961303-31864898-3d80-41ba-8d54-7b48f2844f1a.jpg)
![3333](https://user-images.githubusercontent.com/97405800/171961305-7fe3420d-b1cc-4f04-bdc8-523bceac5581.jpg)













  
Update archive:  
UPD 04.06.22:  
Added STATUS, added more visual features. Rebuild few classes, change some logic and added ADMIN controllers. Also added input form error handling.  
UPD 21.05.22:  
Added attributes to buttons (isAuthorize(), hasRole() etc) for more "user-friendly" interface.  
Added service class, extracted logic from controllers.  
Added some pages for convenience.  
Some visual fixes.  
UPD 15.05.22:  
Added enum Role, added guest enter. Optimized class structure.  
