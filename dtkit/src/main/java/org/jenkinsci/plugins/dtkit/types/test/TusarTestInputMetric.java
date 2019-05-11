package org.jenkinsci.plugins.dtkit.types.test;

import org.jenkinsci.lib.dtkit.model.InputMetricOther;
import org.jenkinsci.lib.dtkit.model.InputType;
import org.jenkinsci.lib.dtkit.util.converter.ConversionException;
import org.jenkinsci.lib.dtkit.util.validator.ValidationError;
import org.jenkinsci.lib.dtkit.util.validator.ValidationException;
import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.dtkit.service.DTKitBuilderFormatValidation;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Gregory Boissinot
 */
public class TusarTestInputMetric extends InputMetricOther {

    @Override
    public InputType getToolType() {
        return InputType.TEST;
    }

    @Override
    public String getToolName() {
        return "Tusar";
    }

    @Override
    public boolean isDefault() {
        return true;
    }

    /**
     * Convert an input file to an output file
     * Give your conversion process
     * Input and Output files are relatives to the filesystem where the process is executed on (like Hudson agent)
     *
     * @param inputFile the input file to convert
     * @param outFile   the output file to convert
     * @param params    the xsl parameters
     * @throws org.jenkinsci.lib.dtkit.util.converter.ConversionException
     *          an application Exception to throw when there is an error of conversion
     *          The exception is catched by the API client (as Hudson plugin)
     */
    @Override
    public void convert(File inputFile, File outFile, Map<String, Object> params) throws ConversionException {
        //Copy input to output
        try {
            FileUtils.copyFile(inputFile, outFile, false);
        } catch (IOException ioe) {
            throw new ConversionException("Conversion error occur- Can't copy file from " + inputFile + "to " + outFile, ioe);
        }
    }

    /*
     *  Gives the validation process for the input file
     *
     * @return true if the input file is valid, false otherwise
     */
    @Override
    public boolean validateInputFile(File inputXMLFile) throws ValidationException {
        List<ValidationError> errors = new DTKitBuilderFormatValidation().isTusarFormat(inputXMLFile);
        setInputValidationErrors(errors);
        return errors.isEmpty();
    }

    /*
     *  Gives the validation process for the output file
     *
     * @return true if the input file is valid, false otherwise
     */
    @Override
    public boolean validateOutputFile(File inputXMLFile) throws ValidationException {
        List<ValidationError> errors = new DTKitBuilderFormatValidation().isTusarFormat(inputXMLFile);
        setOutputValidationErrors(errors);
        return errors.isEmpty();
    }

}
