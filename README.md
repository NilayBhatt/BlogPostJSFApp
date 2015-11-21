# CCSU CS416 Project #3

Nilay Bhatt, Lukasz Brodowski, Travis Moretz

## Brief description of requirements

- All data access is through JPA
- The two JPA Entity Beans are Post, and Author
- JSF Validation used on all forms
- Adding new author form has custom email validation
- Two managed bean controllers which save on submission of author and/or posts.
- AJAX search page for looking up posts by an author

## Important routes

http://localhost:8080/Project3/
http://localhost:8080/Project3/faces/authors/add.xhtml
http://localhost:8080/Project3/faces/posts/create.xhtml
http://localhost:8080/Project3/faces/posts/search.xhtml

## Instructions of flow

1. First create an author.
1. Fail email validation to see custom validation
1. Then create a blog post.
1. Search for blog by author name (case sensitive)
1. Add more authors
1. Add more blog posts
1. Search for different authors
1. Home page displays all blog posts in database
