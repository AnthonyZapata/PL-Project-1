public class leakdetector extends Referencetest<object> {

    private final string description;

    /**
     * initialization of the memory leaks detector.
     * @param referent the object(resource) for which we are looking for leaks.
     */
    public leakdetector(object referent) {
        super(referent, new referencequeue<>());
        this.description = string.valueof(referent);
    }

    /**
     * if exists memory leaks then throws a fail.
     *
     * warn: run this method after delete all references on the checkable object(resource)
     */
    public void assertmemoryleaksnotexist() {
        gcutils.fullfinalization();

        assertions.assertthat(isenqueued())
                  .as("object: " + description + " was leaked")
                  .istrue();
    }

    /**
     * if not exists memory leaks then throws a fail.
     *
     * warn: run this method after delete all references on the checkable object(resource)
     */
    public void assertmemoryleaksexist() {
        gcutils.fullfinalization();

        assertions.assertthat(isenqueued())
                  .as("object: " + description + " already collected by the gc")
                  .isfalse();
    }
}