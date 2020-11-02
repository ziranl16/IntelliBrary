package seedu.intellibrary.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.intellibrary.commons.core.Messages;
import seedu.intellibrary.logic.commands.exceptions.CommandException;
import seedu.intellibrary.logic.parser.CliSyntax;
import seedu.intellibrary.model.Model;
import seedu.intellibrary.model.book.Book;
import seedu.intellibrary.model.book.NameMatchesKeywordPredicate;
import seedu.intellibrary.model.book.NumberContainsKeywordPredicate;
import seedu.intellibrary.model.review.Review;
import seedu.intellibrary.ui.Mode;

/**
 * Searches for the review of the corresponding book.
 */
public class SearchReviewCommand extends Command {

    public static final String COMMAND_WORD = "searchReview";
    public static final String SUGGESTION = "searchReview n/<book name>\n" + "searchReview i/<isbn>\n"
            + "searchReview n/<book name> i/<isbn>";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Search for the stocking of all the books with"
            + "the corresponding keyword and shows them as a list.\n"
            + "Parameters: [" + CliSyntax.PREFIX_NAME + "NAME] "
            + "[" + CliSyntax.PREFIX_ISBN + "ISBN]\n"
            + "Example: " + COMMAND_WORD + " " + CliSyntax.PREFIX_NAME + "a brief history of time";

    private Predicate<Book> predicate;

    private Review review;

    /**
     * Creates a StockCommand to search for the stocking information in each location.
     *
     * @param names The list of names that are used as keyword.
     * @param numbers The list of numbers that are used as keyword.
     */
    public SearchReviewCommand(List<String> names, List<String> numbers) {
        NameMatchesKeywordPredicate nameMatchesKeywordsPredicate;
        NumberContainsKeywordPredicate numberContainsKeywordPredicate;
        if (names != null && !names.get(0).equals("") && numbers != null) {
            nameMatchesKeywordsPredicate = new NameMatchesKeywordPredicate(names);
            numberContainsKeywordPredicate = new NumberContainsKeywordPredicate(numbers);
            predicate = (book -> nameMatchesKeywordsPredicate.test(book)
                    || numberContainsKeywordPredicate.test(book));
        } else if (names != null && !names.get(0).equals("")) {
            predicate = new NameMatchesKeywordPredicate(names);
        } else if (numbers != null) {
            predicate = new NumberContainsKeywordPredicate(numbers);
        } else {
            predicate = Model.PREDICATE_SHOW_ALL_BOOKS;
        }
    };

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredBookList((book -> false), Mode.NORMAL);
        model.updateFilteredBookList(predicate, Mode.REVIEW);
        return new CommandResult(String.format(Messages.MESSAGE_BOOKS_LISTED_OVERVIEW,
                model.getFilteredBookList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof SearchReviewCommand
                && this.predicate.equals(((SearchReviewCommand) other).predicate));
    }
}
