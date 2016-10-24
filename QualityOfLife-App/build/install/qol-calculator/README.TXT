********************************************
Quality of Life (QOL) Calculator Application
********************************************

OVERVIEW:

This application bundle executes code to calculate quality of life (QOL) for a
set of input parameters.   Thes parameters are provided by a properties file
similar to the one defined in the config/demo-qol.properties file.  You will
need to edit the values for your purpose and then execute the calculator
to calculate the necessary indices to calucate the total QOL value.

CONFIGURATION:

The application uses a groovy properties file for configuration of input
parameters to utilize for QOL calculations.   The scale of the values
can be somewhat confusing, as there is a human and a system scale for
many of the input parameters.   The human input scale is [0..100] to
make the value look like a percentage and provide enough room for
easy separation of opinions.   At the system level the [0..100] value
is adjusted to a [-2..2] scale to facilitate some calculations.

To complicate things further, the model center model used to wrap
the QoL calculator needs to input values in a scale of [0..1].
That value will be used to determine where in a range the app
should calculate the input values.  Each input will have a
Bayesian prior of the distribution of improvement possible, and
a scaler in the model will determine where the solution will
fall in the triangle distribution.  The higher the scaler, the 
more complex the solution, and theoretically the more benefits 
received by the technology solution.

The conversion happens through logic in the properties file, which
is why there are different versions provided as templates.  Each
version is provided in the 'config' folder of the distribution and
should be thoroughly documented in the file.  A summary of the files
is provided below.

:: human-qol.properties :: 
- Intended to be read and consumed by humans
- Scaled values are in the human scale of [0..100]
- Use this if you are just testing the app or running locally

:: qol.properties ::
- Designed for integration with model center
- Provides human scaled values of [0..100]
- Scaled values are in the system scale of [-2..2]
- Unfortunately adds more variables to faciliate mc integration

EXECUTION:

To execute the program, use the following format on a command prompt.  

$> bin/qol-calculator {path_to_config_properties} [path_for_output]

If you do not specify a config file, the application will not
operate.  You most specify the configuration file you want to
use for the execution.

If you don not specify an output file path, the application will
default to writing output to the a new 'qol.properties' file in
the directory you initiated the application.   If this file already
exists in the directory it will be overriden.   Consider yourself
warned...

Note the '.bat' extension is only for windows, and the file delimiters
will be different on windows and unix based machines.

<< unix machines >>
$> bin/qol-calculator config/qol.properties [qol.output]

<< windows machines >>
%> bin\qol-calculator.bat config\qol.properties [qol.output]

Feel free to edit the properties file, or add your own and update
the path in the command example accordingly.   If you specify a relative
path, the path is interpreted starting from the working directory.

CREDITS:

Most of the content provided in this application is based on the solid
work performed by the site [http://numbeo.com].   Most of the work in
this app is intended to be an extension of the indices outlined on the
numbeo site, and utilize some of the baseline data as starting points
for calculations to determine changes to quality of life based on 
improvements to measurements caused by public infrastructure projects.

The QOL calculator tool attempts to extend the numbeo calculations and
incorporate a Society Discount Rate (SDR) to project the value of
benefits impacting quality of life into the future.  This future
projection, with the SDR, is utilized to derive a cost-benefit
analysis for public infrastructure projects.   However, none of that
work is possible without the starting point provided by the numbeo.com
site.  Credit below is provided to the founder and CEO of the site.

REFERENCES:

Adamovic, Mladen. (2016). "Quality of Life." Retrieved from http://www.numbeo.com/quality-of-life/.

