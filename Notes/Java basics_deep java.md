Array list using creating object of ArrayList Function
Note:- You can add multiple datatype in same list
```java		
	import java.utils.*;
	List items=new ArrayList();

	items.add();	//To add values in items array
	items.get();	//To access values in items array
	items.remove();	//To remove the value in items array
	items.toString();  //To get all the contents in the array
```
-------------------------------------------------------
hash map list is used to define relation 
```java
	Map relation=new HashMap();
	relation.put("Name","Akshay");	
```
/*
Here we use put to add values in HashMap array the first one is the relative of the second one and we can cal the first one to get the value of second one but you cant call the second one to get first one value.
*/
Note:- All datatypes are supported
```java
	relation.get("Name");	//Use get to access the relative of the asked argument.

	relation.toString();	//This is used to print all the data and there relations
```
Example
=======
```java	
{Name=Akshay, Value=245453, Surname=Bengani, Name2=Naman}

	relation.remove();	// Here u can remove the relation just enter the firstone name

	relation.size(); //This is used to get the total number of relations stored in the HashMap.
```
Note:- basically while using HashMap we can only use the first value to access the map.
--------------------------------------------------------
Print all the values of a string array
```java
	String[] members={"Akshay","Shweta","Sudha","Tej Kumar"};

	for(String name : members)
	{
		System.out.println(name);
	}
```
This is how u can print all the values inside the array
--------------------------------------------------------
Another way of writing this code in generic array

```java
        List<String> members=new ArrayList<String>();
        members.add("Akshay");
        members.add("Shweta");
        members.add("Sudha");
        members.add("Tej Kumar");
        for(String name : members)
        {
            System.out.println(name);
        }
```
This is how u can write this in ArrayList 
Note:- You have to converty the object in String using the angular brackets part at the object creation.

--------------------------------------------------------

