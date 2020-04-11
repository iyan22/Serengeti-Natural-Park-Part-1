# SerengetiNaturalPark
In this document there is an explanation of the project that we have done on the PMOO subject on first grade of Computer Science Engineering at EHU/UPV university.
This projects purpose is to work on concepts that we have been working on the subject, in the first part of the project we are working on concepts such as:
- Attributes
- Constructors
- Methods
- Packages
- Encapsulation
- Singleton
- ArrayLists
- Exceptions
- Files
- Junit 5 tests

## Designing
I have made a project diagram using the tool StarUML, organizing how we were going to implement all the project and what are the attributes, constructors, methods... that have to be implemented in each class.
I have found some aesthetic problems at the time of showing the parent package name (pack::class) or showing the Exception that throw a method, although I have added it on the specific part as was required. Despite these issues I have organized the diagram with the more dependent classes at the top (the ones that need other classes to function), and the more independent classes at the bottom (the ones that only need primitive types and default Java classes).
After finishing with the diagram, using an extension on StarUML I generated all the project “code skeleton”. With this last step I finished the part of the basic design of the project.

![Project Diagram](https://github.com/iyan22/SerengetiNaturalPark/blob/master/uml/Project%20diagram.jpg)


## Coding
Later, I have implemented and document all the attributes, constructors and methods shown in the StarUML diagram for GPS, Drone, Specimen and SerengetiMonitor classes. The class TrackingDevice was given by the PMOO teachers.
Exceptions were created in another package; this is the first contact that we are having with Java hierarchy. These exceptions extend the Exception class. This means, Exception is the superclass of the classes that we are creating, these are the subclasses.
In my opinion, it is a good practice to implement a main program in each of the classes to test the classes little by little, and if an error is found, it can be easily fixed.
After finishing with the implementation and little class simulations I had changed some parts of the diagram to work better or for being clearer.

## New attributes and methods
In my opinion, I think that some attributes, methods were important to implement or necessary to made testing or software maintenance easier.
These are the methods that I have personally added to the first part of the project:
- **GPS class**
  - equals()
- **Drone class**
  - private final int IMAGE_WIDTH = 1000 o private final int IMAGE_HEIGHT = 1000
- **Specimen class**
  - public GPS initialLocation() {...} o public void clearDayRoute() {...} o equals;
- **SerengetiMonitor class**
  - private final int COLLECT_IMAGE_ATTEMPTS = 5

## Name changes
I have changed some methods names for better comprehension.
- **Specimen class**
  - lastPosition() -> lastLocation()
- **SerengetiMonitor class**
  - closestSpecimen() -> closestSpecimenList()
  - captureWithAttemps() -> captureWithAttempts()
  - collectImage() -> collectImagesClosest()
  - updatePositions() -> updatePositionsSpecimens()

## Testing
After finishing the implementation of the basic Classes, we have developed some tests for the methods that were asked to be tested. For this task we are using the tool JUnit 5, the purpose of the tests is to verify the functioning of some methods. In some cases, the expected case is manually created and we compare it with the real one, this means, the one returned by the method.
I have implemented 5 classes to make the tests.
- **GPSTest:** 2 cases for distanceTo().
- **SpecimenTest:** 3 cases for kmsTraveled()
- **SerengetiMonitorTestDefault:** 2 cases for closestSpecimenList()
- **SerengetiMonitorTestDrones:** 2 cases for captureWithAttempts() and other 2
cases for collectImage(), with Drones at the park.
- **SerengetiMonitorTestNoDrones:** 2 cases for captureWithAttempts() and other
2 cases for collectImage(), with no Drones at the park.

## Simulating
To finish with this part of the project we have created a SerengetiSimulator class. The purpose of this class is to simulate a situation where some Drones and Specimens are on the Serengeti National Park and we can monitor what are they doing and send them actions, such as simulating the movement of all the specimens at the park, a drone moving and taking photos of a concrete specimen or knowing what are the specimens nearby a specimen in an specific ratio.
Each simulation is different, some methods (the ones that simulate movement are random).
