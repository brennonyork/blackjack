# Blackjack

'blackjack' is, as the name implies, a typical command line blackjack game. The goal is for you, as the player, to defeat the dealer by scoring a higher hand than the dealer without going over '21'. If you've never played blackjack please reference the wikipedia page [here](http://en.wikipedia.org/wiki/Blackjack) to learn more about the basic game mechanics.

In this version of blackjack there is no gambling and, as such, does not allow 'splitting' or 'doubling down' as both are metrics to achieve a greater payout. Additionally, insurance is not offered by the dealer.

For more detailed code documentation please feel free to check out:

    $ ./bin/documentation

This will launch your preferred web browser for viewing the provided [Marginalia](https://github.com/gdeer81/marginalia) documentation.

### Requirements

* Dealer must hit on soft 17
* Single Deck. The deck is shuffled every 6 rounds.
* Player is not allowed to split cards.
* Keep track of win percentage for the player.
* Provide a readme file explaining how to compile the source and other info that might be interesting.

### Caveats

This has been tested on Macintosh systems and built for Macinotosh and Linux environments. There is no guarantee what will happen if executed on something other than one of these systems.

## Installation

Get ready because there's no installation required! This game, although written in Clojure, will automatically bootstrap and compile itself on first boot with the Leiningen script provided in this bundle.

## Usage

Usage is as simple as executing the following command:

    $ ./bin/blackjack

### Controls

There are three basic controls to the 'blackjack' game. They are:

* h - hit
    + tell the dealer you would like another card
* s - stay
    + tell the dealer you are staying with the current hand
* q - quit
    + quit the game and exit to the terminal

### Board Layout

The game board is laid out in four main sections (lines) corresponding to the dealer's hand, statistics for the player at the current time, the player's current hand, and finally the console for receiving input.

Upon entering the game a board such as follows will be presented:

    :deal:> [:showing [2 :of hearts]]
    :stat:> [:wins 0.00 % :losses 0.00 % :pushes 0.00 %]
    :play:> [[10 :of hearts] [j :of spades]]
    : 01 :>

Breaking this down by line we first see __:deal:>__ which corresponds to the dealer's hand. In this example we can see the dealer is showing a 2 of hearts. Next come the player statistics with __:stat:>__. On this line wins, losses, and pushes (ties) are displayed as a percent of your overall performance. Following the statistics is __:play:>__ which shows the current hand of the player. For every 'hit' in the game this line will grow. In this example the player was dealt a 10 of hearts and a Jack of spades (a pretty good hand!). Finally comes __: 01 :>__ which represents the console where the player will input their action. The '01' in this instance represents the current round of play and will increment as the round increases.

After completing a given round (some number of 'hits' and / or a 'stay') the results will be presented in the following format.

    :LOSE:> Score: 15 vs. Dealer: 18
	          w/Dealer hand: [[3 :of hearts] [6 :of spades] [9 :of clubs]]

In this example we've lost which is represented by the __:LOSE:>__ console prompt. Following the prompt will be the current player score and dealer score as well as the final hand dealt by the dealer.

Periodically you might see a line such as:

    :info:> Shuffling deck.

Don't be afraid, the game is only informing you that the deck is being shuffled new. If you're card counting (shame on you!) you'll want to reset your count at this mark.

The last screen comes once all games are finished and the player 'quits' the session. At this point final statistics will be presented to see how you've faired overall.

    Final Statistics
    :WIN :> Overall: 30
	          Percent: 41.10 %
    :LOSE:> Overall: 38
	          Percent: 52.05 %
    :PUSH:> Overall: 5
	          Percent: 6.85 %

Here we can see overall wins, losses, and pushes as well as the final count for their respective percents.

## Examples

Let's walk through a single round of gameplay. Launching the game I receive this screen:

    Welcome to Blackjack!

    Keyboard actions:
             'h : hit
             's : stay
             'q : quit

    :deal:> [:showing [4 :of spades]]
    :stat:> [:wins 0.00 % :losses 0.00 % :pushes 0.00 %]
    :play:> [[2 :of diamonds] [q :of spades]]
    : 01 :>

This is a tough hand... I've decided to 'hit' with the (h) key and see what happens:

    : 01 :> h
    :deal:> [:showing [4 :of spades]]
    :stat:> [:wins 0.00 % :losses 0.00 % :pushes 0.00 %]
    :play:> [[2 :of diamonds] [q :of spades] [5 :of clubs]]

Wow lucky! Looks like I'm sitting at a 17 and I'm feeling good about this hand given the dealer is showing a 4 of spades. That said I'm going to 'stay' with the (s) key:

    : 01 :> s
    :WIN :> Score: 17 vs. Dealer: 23 (bust)
	          w/Dealer hand: [[j :of hearts] [4 :of spades] [9 :of spades]]

Great scott, we've done it! I'm not much of a gambler (even though there's no money on the table) so I'm going to call it 'quit's here with the (q) key:

    Final Statistics
    :WIN :> Overall: 1
	          Percent: 100.00 %
    :LOSE:> Overall: 0
	          Percent: 0.00 %
    :PUSH:> Overall: 0
	          Percent: 0.00 %

Overall I think we did pretty good!
