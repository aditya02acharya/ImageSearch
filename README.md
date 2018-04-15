# Reinforcement Learning Model for Image Search task.

An Optimal Control and state estimation model is applied to an image search task experiment by Tseng and Howes (2015). The learned model
shows the adaptive behaviour of the user to utility and the information processing constraints.

## The Task

The task was to look for an image that included as many of the target
features as possible and as quickly as possible. The target description
contained five unique target features, e.g., castle, could, sky, tree, water.
The task was similar to a visual search task in which people are
asked to search for a target among distractors. However, the task was
different n three aspect: (1) the description of the five target features
comprised of high level features like ‘cloud’ or ‘sky’ rather that lower
level features like colour or shape; (2) participants were allowed to
select any image in the set; (3) The results of the search was neither
correct nor incorrect. Rather participants were given points according
to a utility function in terms of the value of the image and the
search time. Where, the value of the image was a function of target
features that it contained.

## Model Description
The model here tries to learn when to decide to click an image assuming
this is the best image in the environment or make an eye
movement in search of better images. This learning process can be
emulated with control knowledge. The control knowledge is represented
as a mapping between the beliefs and actions, which is learnt
with a reinforcement learning algorithm, Q-learning. 

Before learning, an empty Qtable was assumed in which the values (i.e., Q-values) of all belief action
pairs were zero. The model therefore started with no control
knowledge and action selection was entirely random. The model
was then trained until performance plateaued (requiring 100,000 trials).
The model explored the action space using an epsilon-greedy
exploration.


## License

This project is licensed under the MIT License.
