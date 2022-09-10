/**
 * @package ku.cs.oakcoding.app.helpers
 * 
 * @brief OakCoding's application helpers are utility types that perform tasks unrelated to user interfaces.
 * 
 * Helper types are not the same as Service types.
 * 
 * For example,
 * Consider an event: "save profile image submitted by a user to directory A"
 * 
 * Steps:
 *  1.  User submit an image URL via app registration form; the controller class that is responsible
 *      to invoke a model class' method in order to grab the URL String from the form is a service class
 *  2.  After validating the URL String, it is used to reference a real image file on the file system which
 *      can then be copied to directory A; the class that perform the copying step is such a helper class
 * 
 * User only acknowledge the process of submitting image URL but they do not necessarily have to know the underlying steps.
 * Those underlying steps are performed by helper types.
 */
package ku.cs.oakcoding.app.helpers;
