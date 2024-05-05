# Minesweeper
This application is a simple java-based game of Minesweeper, that is run in a terminal and controlled with text inputs.

## instructions
On running main, the user is presented with a grid and is prompted to input a coordinate. 
Coordinates are formatted as (rows)(column), for example 'd2' would select the 4th row down, 2nd column across.
Selecting a coordinate will show the hidden value of that space and display a new grid for the next game state.
The space will either be a mine, in which case the game is lost, or will say how many mines are adjacent to that space in a 3x3 area.
If the space has no mines next to it, all other spaces around that space will be selected, cascading if multiple touching spaces are not next to a mine.
The user can also place an exclaimation mark before the coordinate input, for example '!a7', which will mark that space.
Marked spaces will display as '!' until selected or unmarked. 
If the user selects all spaces without a mine, the game is won.

## future versions
Future versions should include an option for the user to input a grid size and mines count at the start of the game, and for the input to be abstracted to work with different mediums, such as a GUI or from web.