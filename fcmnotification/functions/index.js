const functions = require("firebase-functions");
const admin = require("firebase-admin");
const { getDatabase } = require("firebase-admin/database");
admin.initializeApp();
const database = getDatabase();

exports.sendJobNotification = functions.database.ref("/jobs/").onUpdate(async (change, context) => {
    let beforeKeys = [];
    let afterKeys = [];

    change.before.forEach((beforeSnapshot) => {
        beforeKeys.push(beforeSnapshot.key);
    });
    change.after.forEach((afterSnapshot) => {
        afterKeys.push(afterSnapshot.key);
    });

    const latest = afterKeys.filter((val) => {
        return beforeKeys.indexOf(val) == -1;
    });

    const ref = database.ref(`/jobs/${latest[0]}/`);
    const latestJob = await ref.get();

    functions.logger.log(`title: ${latestJob.val()["jobTitle"]}, body: ${latestJob.val()["priority"]}`);

    const message = {
        notification: {
          title: `New job in your area: ${latestJob.val()["jobTitle"]}`,
          body: `Priority: ${latestJob.val()["priority"]}`
        },
        android: {
          notification: {

          }
        },
        topic: "newJobs",
    };
      

    await admin.messaging().send(message);

    return functions.logger.log(message);

    

});
