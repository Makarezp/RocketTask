# SpaceX

### Tech stack:
- Coroutines
- Flows
- Room
- Paging 3 library

For API I used V4 as V3 is deprecated and doesn't serve any new data after November 2020

### Features:
- Paging
- Filtering
- Continuous swipe
- Swipe to refresh


### Known bugs and cut corners
- Filtering doesn't work offline (local filtering would require more logic to integrate with paging lib, decided to cut it out for simplicity)
- Funny behaviour of Launches top separator, it's because of paging library, to fix it separator should be moved to top adapter.
- Null links doesn't work (implementation should't show action option if link is null)
- Ongoing mission dates are showing with minus sign (a little bit of logic, the requirement was to show +/- depending on state I've cut it out)
- No empty results pages
- No multi year filtering.

#### Espresso:

I really just made very basic UI tests. I guess for task like this setting up infrastructure for proper espresso tests would be too much (mock web server,  hilt integration, okhttp idling resource etc, possibly Kotlin DSL etc..).


### Findings
I found this task as a good opportunity for trying and assessing couple of new things.
First of all paging 3 library.

Paging 3
##### Pros:
- Quite easy integration of paging
- Support of room for offline features

##### Cons:
- Not unit testable (supposedly there's a plan by Google devs to make it testable)
- Some APIs are very clunky to use
- Poisons every architectural layer with kinda black box closed APIs
- Definitely not ready yet, a lot of experimental APIs

#### Verdict:
Very nice lib for startups or smaller apps/features, might be risky to use in bigger projects in flagship features even when ready.


#### Flows

##### Pros:
- Seamlessly integrates with each layer of coroutine based project
- Powerful and clean APIs
- More readable than RxJava
- Can replace both RxJava and LiveData at once
- Support for android lifecycle in presentation layer

##### Cons:
- Lot of deprecated APIs
- Difficult to test (probably little bit more compared to RxJava)
- As a replacement for LiveData in viewmodels, steeper learning curve.
- Sometimes not clear online documentation.

#### Verdict:
Flows as a replacement for RxJava + LiveData worked really good. Adds bit of complexity in viewmodels but having unified API is really worth it.
