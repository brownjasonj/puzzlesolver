# puzzlesolver
Solution to solve placing geometric shapes onto a square board

To run this thing you'll need to have Java installed and Gradle.

Get the repository, then in a terminal from the root directory of the project (i.e., what you download) type

gradle bootRun

Once the application is running you need to post one of the json files (found in the resources directory) to

http://localhost:30001/solveproblem

use Postman to do that.  It needs to be posted as type 'application/json'.

