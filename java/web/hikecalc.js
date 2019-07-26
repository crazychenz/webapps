/**
 * Local Hike Calculator Validation and GUI Support.
 * @author Vincent Agriesti
 */

/** Minimum month within season. */
const MIN_MONTH = 5;
/** Maximum month within season. */
const MAX_MONTH = 8;
/** Maximum date within the last month within the season. */
const MAX_MONTH_DATE = 30;
/** Maximum year allowed for calculations. */
const MAX_YEAR = 2025;

/**
 * Indicates if a given date is within the season.
 * @param  date The Date to check against season.
 * @return Returns true if in season or false if not in first element of an array.
 */
function disableOffSeason(date) {
    if (date.getMonth() < MIN_MONTH || date.getMonth() > MAX_MONTH) {
        return [false];
    }
    return [true];
}

/**
 * jQuery datepicker widget with callback to disableOffSeason so that
 * only the dates within the season can be selected.
 */
$( function() {
    $( "#datepicker" ).datepicker({
        minDate: 1,
        maxDate: new Date(MAX_YEAR, MAX_MONTH, MAX_MONTH_DATE),
        constrainInput: true,
        beforeShowDay: disableOffSeason,
    });
} );

/**
 * Local validation of the hikers field.
 */
function validateForm() {
    var hikers = document.forms["calc_form"]["hikers"].value;
    if (hikers < 1 || hikers > 10) {
        alert("Hikers must be 1 to 10.");
        return false;
    }
}

/**
 * When the hike is changed, the durations select element
 * is updated so only valid duration values appear.
 */
function onHikeChange() {
    var hike = document.forms["calc_form"]["hike"].value;
    var durations = document.forms["calc_form"]["duration"];
    var duration = document.forms["calc_form"]["duration"].value;
    var options;

    // Wipe the old options.
    for (i = durations.options.length - 1 ; i >= 0 ; i--)
    {
        durations.remove(i);
    }

    // Get the new option values depending on hike.
    if (hike == "GARDINER") {
        options = ['3', '5'];
    }
    else if (hike == "HELLROARING") {
        options = ['2', '3', '4'];
    }
    else if (hike == "BEATEN") {
        options = ['5', '7'];
    }

    // Add the new option elements in.
    for (i = 0; i < options.length; i++)
    {
        var opt = document.createElement('option');

        // Keep the old value if possible
        if (duration == options[i]) {
            opt.selected = 'selected';
        }

        opt.text = options[i];
        opt.value = options[i];

        durations.add(opt, null);
    }
}
